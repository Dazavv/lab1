package com.example.secureapi.lab1.jwt;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {
    public static JwtAuthentication generate(Claims claims) {
        JwtAuthentication auth = new JwtAuthentication();
        auth.setLogin(claims.getSubject());
        auth.setAuthenticated(true);
        return auth;
    }
}