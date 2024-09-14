package com.dvk.ct250backend.domain.auth.entity;

import com.dvk.ct250backend.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

//@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends BaseEntity implements GrantedAuthority {
    @Id
    @GeneratedValue
    Long roleId;

    @NotBlank(message = "Role name is required")
    String roleName;
    String description;
    boolean active;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    List<User> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "permission_role", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    List<Permission> permissions;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
