package com.example;

import org.springframework.boot.SpringApplication;

public class TestOutboxPatternApplication {

    public static void main(String[] args) {
        SpringApplication.from(OutboxPatternApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
