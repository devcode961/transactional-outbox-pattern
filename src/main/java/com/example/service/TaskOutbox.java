package com.example.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskOutbox {

    private UUID id;
    private Task task;
    private OffsetDateTime createdAt;
    private boolean sentToBus;

}
