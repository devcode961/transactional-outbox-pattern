package com.example;

import com.example.entity.TaskEntity;
import com.example.entity.TaskOutboxEntity;
import com.example.repository.TaskOutboxRepository;
import com.example.repository.TaskRepository;
import com.example.service.Task;
import com.example.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.shaded.org.awaitility.Awaitility;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestcontainersConfiguration.class)
@EmbeddedKafka(partitions = 1, topics = "tasks", ports = 9092)
public class TaskServiceIntegrationTests {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskOutboxRepository taskOutboxRepository;

    // Add test methods here
    @Test
    void testCreateTask() {
        // Add test code here
        Task task = new Task();
        task.setName("Task 1");
        Task createdTask = taskService.createTask(task);
        TaskEntity taskEnt = taskRepository.findById(createdTask.getId()).orElseThrow(
                () -> new RuntimeException("Task not found")
        );
        assert taskEnt.getName().equals("Task 1");
    }

    @Test
    void test_Transaction_Outbox() {
        // Add test code here
        Task task = new Task();
        task.setName("Task 1");
        Task createdTask = taskService.createTask(task);
        TaskEntity taskEnt = taskRepository.findById(createdTask.getId()).orElseThrow(
                () -> new RuntimeException("Task not found")
        );
        assert taskEnt.getName().equals("Task 1");

        TaskOutboxEntity taskOutboxEntity = taskOutboxRepository.findByTaskId(createdTask.getId()).orElseThrow(
                () -> new RuntimeException("Task Outbox Entry not found")
        );

        assert taskOutboxEntity.getTask().getId().equals(createdTask.getId());
        assert !taskOutboxEntity.isSentToBus();

    }

    @Test
    void test_Transaction_Outbox_Publish_Message() {
        // Add test code here
        Task task = new Task();
        task.setName("Task 1");
        Task createdTask = taskService.createTask(task);
        TaskEntity taskEnt = taskRepository.findById(createdTask.getId()).orElseThrow(
                () -> new RuntimeException("Task not found")
        );
        assert taskEnt.getName().equals("Task 1");

        Awaitility.await().untilAsserted(() -> {
            TaskOutboxEntity taskOutboxEntity = taskOutboxRepository.findByTaskId(createdTask.getId()).orElseThrow(
                    () -> new RuntimeException("Task Outbox Entry not found")
            );

            assert taskOutboxEntity.getTask().getId().equals(createdTask.getId());
            assert taskOutboxEntity.isSentToBus();
        });

    }
}
