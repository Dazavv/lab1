package com.example.secureapi.lab1.controller;

import com.example.secureapi.lab1.dto.MakeTaskRequest;
import com.example.secureapi.lab1.dto.TaskResponse;
import com.example.secureapi.lab1.dto.UserResponse;
import com.example.secureapi.lab1.entity.User;
import com.example.secureapi.lab1.service.TaskService;
import com.example.secureapi.lab1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<User> users = userService.getAllUsers();

        List<UserResponse> response = users.stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getLogin(),
                        u.getName()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/task")
    public ResponseEntity<TaskResponse> makeTask(
            Principal principal,
            @RequestBody MakeTaskRequest request
    ) {
        User user = userService.getByLogin(principal.getName());

        String safeTitle = HtmlUtils.htmlEscape(request.getText());
        MakeTaskRequest safeReq = new MakeTaskRequest(safeTitle);

        TaskResponse response = taskService.createTask(user, safeReq);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<TaskResponse> response = taskService.getAllTasks().stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        HtmlUtils.htmlEscape(task.getText()),
                        task.getUserId()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

}
