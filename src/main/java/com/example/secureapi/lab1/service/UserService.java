package com.example.secureapi.lab1.service;

import com.example.secureapi.lab1.entity.User;
import com.example.secureapi.lab1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository authRepository;

    public User getByLogin(String login) {
        return authRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    public boolean checkExistedUser(String login) {
        return authRepository.existsByLogin(login);
    }

    public void saveNewUser(User user) {
        authRepository.save(user);
    }

    public List<User> getAllUsers() {
        return authRepository.findAll();
    }

}
