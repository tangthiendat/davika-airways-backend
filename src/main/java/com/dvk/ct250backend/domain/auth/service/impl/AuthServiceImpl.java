package com.dvk.ct250backend.domain.auth.service.impl;

import com.dvk.ct250backend.app.exception.IdInValidException;
import com.dvk.ct250backend.domain.auth.dto.UserDTO;
import com.dvk.ct250backend.domain.auth.dto.request.AuthRequest;
import com.dvk.ct250backend.domain.auth.dto.response.AuthResponse;
import com.dvk.ct250backend.domain.auth.entity.Role;
import com.dvk.ct250backend.domain.auth.entity.User;
import com.dvk.ct250backend.domain.auth.mapper.UserMapper;
import com.dvk.ct250backend.domain.auth.repository.RoleRepository;
import com.dvk.ct250backend.domain.auth.repository.UserRepository;
import com.dvk.ct250backend.domain.auth.service.AuthService;
import com.dvk.ct250backend.infrastructure.audit.AuditAwareImpl;
import com.dvk.ct250backend.infrastructure.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    JwtUtils jwtUtils;
    JwtDecoder jwtDecoder;
    AuditAwareImpl auditAware;

    @Override
    public UserDTO register(UserDTO userDTO) throws IdInValidException {
        boolean isEmailExist = this.userRepository.existsByEmail(userDTO.getEmail());
        if (isEmailExist) {
            throw new IdInValidException("Email " + userDTO.getEmail() + " already exists, please use another email.");
        }
        User user = userMapper.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepository.findByRoleName("USER");
        if (role == null) {
            role = new Role();
            role.setRoleName("USER");
            role.setIsActive(true);
            role.setDescription("User role");
            role = roleRepository.save(role);
        }

        user.setRole(role);
        return userMapper.toUserDTO(userRepository.save(user));
    }

    @Override
    public AuthResponse login(AuthRequest authRequest, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        AuthResponse.UserLogin userLogin = new AuthResponse.UserLogin(
                user.getUserId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        // Store refresh token in http only cookie
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        // refreshTokenCookie.setSecure(true); // HTTPS
        refreshTokenCookie.setPath("/");
        // prevent CSRF attacks
        refreshTokenCookie.setAttribute("SameSite", "Strict");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days

        response.addCookie(refreshTokenCookie);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .user(userLogin)
                .build();
    }

    @Override
    public AuthResponse refreshAccessToken(String refreshToken) {
        Jwt jwtRefreshToken = jwtDecoder.decode(refreshToken);
        String username = jwtUtils.getUsername(jwtRefreshToken);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        AuthResponse.UserLogin userLogin = new AuthResponse.UserLogin(
                user.getUserId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
        return AuthResponse.builder()
                .accessToken(jwtUtils.generateAccessToken(user))
                .user(userLogin)
                .build();
    }

   @Override
   public void logout(HttpServletResponse response) throws IdInValidException {
    String email = auditAware.getCurrentAuditor().orElse("");

    if (email.isEmpty()) {
        throw new IdInValidException("Access Token not valid");
    }

    // Clear the authentication context
    SecurityContextHolder.clearContext();

    // Remove the refresh token cookie
    Cookie refreshTokenCookie = new Cookie("refresh_token", null);
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setPath("/");
    refreshTokenCookie.setMaxAge(0); // Set max age to 0 to delete the cookie

    response.addCookie(refreshTokenCookie);
}
}