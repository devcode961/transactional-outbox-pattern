package com.example.scheduler;

import com.example.service.TaskOutboxService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskOutboxScheduler {

    private final TaskOutboxService taskOutboxService;

    @Scheduled(fixedDelayString = "10000")
    @SchedulerLock(name = "task-outbox")
    public void publishBatchToEventBus() {
        taskOutboxService.publishNextBatchToEventBus(10);
    }
}
