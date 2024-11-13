package com.example;

import com.example.entity.TaskEntity;
import com.example.service.EventBusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EventBusServiceTests {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private EventBusService eventBusService;

    private TaskEntity taskEntity;

    @BeforeEach
    void setUp() {
        taskEntity = new TaskEntity();
        taskEntity.setId(UUID.randomUUID());
        taskEntity.setName("Task 1");
    }

    @Test
    void testPublishTask() {
        eventBusService.publishTask(taskEntity);

        verify(kafkaTemplate, times(1))
                .send("tasks", taskEntity.getId().toString(), taskEntity);
    }
}
