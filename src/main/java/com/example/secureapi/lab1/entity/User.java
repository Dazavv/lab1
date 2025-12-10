package com.example.secureapi.lab1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;
}

