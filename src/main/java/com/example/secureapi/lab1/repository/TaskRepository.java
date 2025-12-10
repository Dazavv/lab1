package com.example.secureapi.lab1.repository;

import com.example.secureapi.lab1.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
