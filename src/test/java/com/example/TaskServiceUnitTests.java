package com.example;

import com.example.entity.TaskEntity;
import com.example.entity.TaskOutboxEntity;
import com.example.repository.TaskOutboxRepository;
import com.example.repository.TaskRepository;
import com.example.service.Task;
import com.example.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceUnitTests {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskOutboxRepository taskOutboxRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private TaskEntity taskEntity;
    private TaskOutboxEntity taskOutboxEntity;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setName("Task 1");

        taskEntity = new TaskEntity();
        taskEntity.setName("Task 1");

        taskOutboxEntity = new TaskOutboxEntity();
        taskOutboxEntity.setTask(taskEntity);
        taskOutboxEntity.setCreatedAt(ZonedDateTime.now());
        taskOutboxEntity.setSentToBus(false);
    }

    @Test
    void testCreateTask() {
        var id = UUID.randomUUID();
        taskEntity.setId(id);
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);
        when(taskOutboxRepository.save(any(TaskOutboxEntity.class))).thenReturn(taskOutboxEntity);

        Task createdTask = taskService.createTask(task);

        assertEquals(id, createdTask.getId());
        assertEquals("Task 1", createdTask.getName());
        verify(taskRepository, times(1)).save(any(TaskEntity.class));
        verify(taskOutboxRepository, times(1)).save(any(TaskOutboxEntity.class));
    }

    @Test
    void testGetAllTasks() {
        var id = UUID.randomUUID();
        taskEntity.setId(id);
        when(taskRepository.findAll()).thenReturn(List.of(taskEntity));

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(1, tasks.size());
        assertEquals(id, tasks.getFirst().getId());
        assertEquals("Task 1", tasks.getFirst().getName());
        verify(taskRepository, times(1)).findAll();
    }
}
