package org.example.storemanagementbestpractice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String mySecretEnv;

    @Value("${expiration.time}")
    private long expirationTimeEnv;

    private SecretKey secretKey;

    @PostConstruct
    public void initializeSecretKey() {
        secretKey = Keys.hmacShaKeyFor(mySecretEnv.getBytes());
    }

    public String generateToken(String username, UUID userId) {
        log.info("expiriation time {}", expirationTimeEnv);
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId);
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTimeEnv))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public UUID extractId(String token) {
        final Claims claims = getClaims(token);
        return UUID.fromString(claims.get("id", String.class));
    }

    public boolean validateToken(String username, UserDetails userDetails, String token) {
        return userDetails.getUsername().equals(username)
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}
