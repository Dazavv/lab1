package com.example.secureapi.lab1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MakeTaskRequest {
    private String text;
}
