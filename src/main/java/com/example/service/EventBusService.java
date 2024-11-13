package com.example.service;

import com.example.entity.TaskEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EventBusService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishTask(TaskEntity task) {
        kafkaTemplate.send("tasks", task.getId().toString(), task);
    }
}
