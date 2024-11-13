package com.example.service;

import com.example.repository.TaskOutboxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskOutboxService {

    private static final Logger log = LoggerFactory.getLogger(TaskOutboxService.class);
    private final TaskOutboxRepository taskOutboxRepository;

    private final EventBusService eventBusService;

    //write method to publish task to kakfa event bus
    @Transactional
    public void publishNextBatchToEventBus(int batchSize) {

        var taskOutboxEntities = taskOutboxRepository.findAllBySentToBus(false,
                PageRequest.of(0,
                        batchSize,
                        Sort.by("createdAt").ascending()));

        log.info("Publishing {} tasks to event bus", taskOutboxEntities.size());

        taskOutboxEntities
                .forEach(taskOutboxEntity -> {
                    //publish to kafka
                    eventBusService.publishTask(taskOutboxEntity.getTask());
                    taskOutboxEntity.setSentToBus(true);
                    taskOutboxRepository.save(taskOutboxEntity);
                });
    }


}
