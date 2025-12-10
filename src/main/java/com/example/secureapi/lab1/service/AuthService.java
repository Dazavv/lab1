package com.example.secureapi.lab1.service;

import com.example.secureapi.lab1.dto.JwtResponse;
import com.example.secureapi.lab1.entity.User;
import com.example.secureapi.lab1.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService authUserService;
    private final JwtService jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(String login, String password) {
        final User user = authUserService.getByLogin(login);
        if (passwordEncoder.matches(password, user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            return new JwtResponse(accessToken);
        } else {
            throw new RuntimeException("Password is wrong");
        }
    }

    public JwtResponse register(String login, String password, String name) {
        if (authUserService.checkExistedUser(login)) {
            throw new RuntimeException("User with login: " + login + " already exists");
        }

        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);

        authUserService.saveNewUser(user);
        String accessToken = jwtProvider.generateAccessToken(user);

        return new JwtResponse(accessToken);
    }
}
