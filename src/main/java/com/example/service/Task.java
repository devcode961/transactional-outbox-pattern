package com.example.service;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class Task {

    private UUID id;

    private String name;
}
