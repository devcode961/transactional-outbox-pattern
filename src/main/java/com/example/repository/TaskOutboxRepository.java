package com.example.repository;

import com.example.entity.TaskOutboxEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskOutboxRepository extends JpaRepository<TaskOutboxEntity, UUID> {
    //write method to find by task id
    Optional<TaskOutboxEntity> findByTaskId(UUID taskId);

    //write method to find by sent to bus make it pageable
    List<TaskOutboxEntity> findAllBySentToBus(boolean sentToBus, Pageable pageable);
}
