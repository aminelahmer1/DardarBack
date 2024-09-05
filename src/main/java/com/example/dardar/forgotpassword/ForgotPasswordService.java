package com.example.dardar.forgotpassword;

import com.example.dardar.security.jwt.JwtUtils;
import com.example.dardar.security.services.UserDetailsImpl;
import com.example.dardar.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailService emailService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public void sendPasswordResetEmail(String username) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
        String userDetailsEmail = userDetails.getEmail();
        // Generate a password reset token
        String token = jwtUtils.generateTokenFromUsername(username);

        // Send email containing password reset link with token
        String resetLink = "http://localhost:8081/reset-password?token=" + token;
        emailService.sendPasswordResetEmail(userDetailsEmail, resetLink);
    }
}
