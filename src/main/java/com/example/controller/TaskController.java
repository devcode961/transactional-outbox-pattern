package com.example.controller;

import com.example.service.Task;
import com.example.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody @Validated Task task) {
        var createdTask = taskService.createTask(task);
        return ResponseEntity.ok().body(createdTask);
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        var tasks = taskService.getAllTasks();
        return ResponseEntity.ok().body(tasks);
    }


}
