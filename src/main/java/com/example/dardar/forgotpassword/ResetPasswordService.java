package com.example.dardar.forgotpassword;

import com.example.dardar.entities.User;
import com.example.dardar.repositories.UserRepository;
import com.example.dardar.security.jwt.JwtUtils;
import com.example.dardar.security.services.UserDetailsImpl;
import com.example.dardar.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void resetPassword(String token, String newPassword) {
        // Extract username from token
        String usernameFromJwtToken = jwtUtils.getUserNameFromJwtToken(token);

        // Load user details by username
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(usernameFromJwtToken);

        // Get the user entity from the loaded UserDetails
        User user = userRepository.findByUsername(usernameFromJwtToken)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Encode the new password
        String encodedPassword = passwordEncoder.encode(newPassword);

        // Update user's password with the encoded password
        user.setPassword(encodedPassword);

        // Save the updated user in the database
        userRepository.save(user);
    }
}
