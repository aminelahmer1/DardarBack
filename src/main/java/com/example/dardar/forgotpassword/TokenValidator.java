package com.example.dardar.forgotpassword;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class TokenValidator {

    @Value("${coco.app.jwtSecret}")
    private String jwtSecret;

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            return false; // Invalid token format
        }
    }

    private Key key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
}
