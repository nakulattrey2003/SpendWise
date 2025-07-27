package com.backend.spendwise.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil 
{
    // Secret key generated using Keys.hmacShaKeyFor
    private static final long EXPIRATION_TIME = 86400000L; // 1 day in milliseconds
    private static final byte[] SECRET_BYTES = "0123456789ABCDEF0123456789ABCDEF".getBytes(StandardCharsets.UTF_8);

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_BYTES);

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}