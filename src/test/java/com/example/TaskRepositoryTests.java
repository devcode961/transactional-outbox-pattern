package com.example;

import com.example.entity.TaskEntity;
import com.example.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTests {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void test_SaveTask() {
        TaskEntity task = new TaskEntity();
        task.setName("Task 1");
        TaskEntity createdTask = taskRepository.save(task);
        assert createdTask.getName().equals("Task 1");
    }

    @Test
    void test_FindTask() {
        TaskEntity task = new TaskEntity();
        task.setName("Task 1");
        TaskEntity createdTask = taskRepository.save(task);
        var task_id = createdTask.getId();

        TaskEntity taskEnt = taskRepository.findById(task_id).orElseThrow(
                () -> new RuntimeException("Task not found")
        );
        assert taskEnt.getName().equals("Task 1");
        assert taskEnt.getId().equals(task_id);
    }

}
