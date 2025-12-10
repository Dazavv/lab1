package com.example.secureapi.lab1.service;

import com.example.secureapi.lab1.dto.MakeTaskRequest;
import com.example.secureapi.lab1.dto.TaskResponse;
import com.example.secureapi.lab1.entity.Task;
import com.example.secureapi.lab1.entity.User;
import com.example.secureapi.lab1.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskResponse createTask(User user, MakeTaskRequest request) {
        Task task = new Task();
        task.setText(request.getText());
        task.setUser(user);

        taskRepository.save(task);

        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setText(task.getText());
        response.setUserId(user.getId());

        return response;
    }
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(task -> {
                    TaskResponse response = new TaskResponse();
                    response.setId(task.getId());
                    response.setText(task.getText());
                    response.setUserId(task.getUser().getId());
                    return response;
                })
                .collect(Collectors.toList());
    }
}
