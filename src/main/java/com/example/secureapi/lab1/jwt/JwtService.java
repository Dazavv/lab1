package com.example.secureapi.lab1.jwt;

import com.example.secureapi.lab1.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Service
public class JwtService {
    private final SecretKey jwtAccessSecretKey;

    public JwtService(@Value("${jwt.secret}") String jwtAccessSecretKey) {
        this.jwtAccessSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecretKey));
    }

    public String generateAccessToken(@NonNull User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(15).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .subject(user.getLogin())
                .expiration(accessExpiration)
                .signWith(jwtAccessSecretKey)
                .compact();
    }

    public boolean validateToken(@NonNull String token) {
        try {
            Jwts.parser()
                    .verifyWith(jwtAccessSecretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public Claims getClaims(@NonNull String token) {
        return Jwts.parser()
                .verifyWith(jwtAccessSecretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}