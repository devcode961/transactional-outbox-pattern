package com.example.service;

import com.example.entity.TaskEntity;
import com.example.entity.TaskOutboxEntity;
import com.example.repository.TaskOutboxRepository;
import com.example.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskOutboxRepository taskOutboxRepository;

    @Transactional(rollbackOn = Exception.class)
    public Task createTask(Task task) {

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(task.getName());
        var createdTaskEntity = taskRepository.save(taskEntity);

        TaskOutboxEntity taskOutboxEntity = new TaskOutboxEntity();
        taskOutboxEntity.setTask(createdTaskEntity);
        taskOutboxEntity.setCreatedAt(ZonedDateTime.now());
        taskOutboxEntity.setSentToBus(false);

        taskOutboxRepository.save(taskOutboxEntity);
        task.setId(createdTaskEntity.getId());
        return task;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskEntity -> {
                    Task task = new Task();
                    task.setId(taskEntity.getId());
                    task.setName(taskEntity.getName());
                    return task;
                })
                .collect(Collectors.toList());
    }
}
