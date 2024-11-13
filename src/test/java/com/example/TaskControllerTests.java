package com.example;

import com.example.service.Task;
import com.example.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
public class TaskControllerTests {

    private static final String API_TASK = "/api/task";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    // Add test methods here to test controller
    @Test
    void test_CreateTask() throws Exception {
        // Create a Task object to be returned by the mock
        Task task = new Task();
        task.setId(UUID.randomUUID());
        task.setName("Task 1");

        // Configure the mock to return the Task object
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        // Add test code here
        String taskJson = "{\"name\":\"Task 1\"}";

        mockMvc.perform(post(API_TASK)
                        .contentType("application/json")
                        .content(taskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Task 1"))
                .andExpect(jsonPath("$.id").exists())
                .andDo(result -> {
                    System.out.println(result.getResponse().getContentAsString());
                });
    }

    @Test
    void test_GetAllTasks() throws Exception {
        // Create a Task object to be returned by the mock
        Task task1 = new Task();
        task1.setId(UUID.randomUUID());
        task1.setName("Task 1");

        Task task2 = new Task();
        task2.setId(UUID.randomUUID());
        task2.setName("Task 2");


        // Configure the mock to return the Task object
        when(taskService.getAllTasks())
                .thenReturn(List.of(task1, task2));

        //perform the request
        mockMvc.perform(get(API_TASK))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Task 1"))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[1].name").value("Task 2"))
                .andExpect(jsonPath("$[1].id").exists())
                .andDo(result -> {
                    System.out.println(result.getResponse().getContentAsString());
                });
    }

}
