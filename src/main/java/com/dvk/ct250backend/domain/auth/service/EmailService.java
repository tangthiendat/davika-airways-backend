package com.dvk.ct250backend.domain.auth.service;

import com.dvk.ct250backend.domain.auth.entity.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendVerificationEmail(User user, String siteURL, String verifyToken) throws MessagingException, UnsupportedEncodingException;
    void sendPasswordResetEmail(User user, String resetURL) throws MessagingException, UnsupportedEncodingException;
}
