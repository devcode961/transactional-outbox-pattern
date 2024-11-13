package com.example;

import com.example.entity.TaskEntity;
import com.example.entity.TaskOutboxEntity;
import com.example.repository.TaskOutboxRepository;
import com.example.service.EventBusService;
import com.example.service.TaskOutboxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskOutboxServiceTests {

    @Mock
    private TaskOutboxRepository taskOutboxRepository;

    @Mock
    private EventBusService eventBusService;

    @InjectMocks
    private TaskOutboxService taskOutboxService;

    private TaskOutboxEntity taskOutboxEntity;

    @BeforeEach
    void setUp() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(UUID.randomUUID());
        taskEntity.setName("Task 1");

        taskOutboxEntity = new TaskOutboxEntity();
        taskOutboxEntity.setTask(taskEntity);
        taskOutboxEntity.setCreatedAt(ZonedDateTime.now());
        taskOutboxEntity.setSentToBus(false);
    }

    @Test
    void testPublishNextBatchToEventBus() {
        when(taskOutboxRepository.findAllBySentToBus(false, PageRequest.of(0, 10, Sort.by("createdAt").ascending())))
                .thenReturn(List.of(taskOutboxEntity));

        taskOutboxService.publishNextBatchToEventBus(10);

        verify(eventBusService, times(1)).publishTask(taskOutboxEntity.getTask());
        verify(taskOutboxRepository, times(1)).save(taskOutboxEntity);
        assert taskOutboxEntity.isSentToBus();
    }

    @Test
    void testPublishNextBatchToEventBus_NoTasks() {
        when(taskOutboxRepository.findAllBySentToBus(false, PageRequest.of(0, 10, Sort.by("createdAt").ascending())))
                .thenReturn(List.of());

        taskOutboxService.publishNextBatchToEventBus(10);

        verify(eventBusService, never()).publishTask(any());
        verify(taskOutboxRepository, never()).save(any());
    }
}
