package com.example.secureapi.lab1.controller;

import com.example.secureapi.lab1.dto.JwtResponse;
import com.example.secureapi.lab1.dto.LoginRequest;
import com.example.secureapi.lab1.dto.RegisterRequest;
import com.example.secureapi.lab1.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request) {

        String safeLogin = HtmlUtils.htmlEscape(request.getLogin());
        String safePassword = HtmlUtils.htmlEscape(request.getPassword());
        String safeName = HtmlUtils.htmlEscape(request.getName());

        JwtResponse token = authService.register(
                safeLogin,
                safePassword,
                safeName
        );
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {

        String safeLogin = HtmlUtils.htmlEscape(request.getLogin());
        String safePassword = HtmlUtils.htmlEscape(request.getPassword());

        JwtResponse token = authService.login(safeLogin, safePassword);
        return ResponseEntity.ok(token);
    }
}
