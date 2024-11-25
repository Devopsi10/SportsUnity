package com.todoapp.controller;

import com.todoapp.model.Task;
import com.todoapp.model.User;
import com.todoapp.model.UserType;
import com.todoapp.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    // Post a new task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        logger.info("Creating task for userId: {}", task.getUserId());

        if (task.getUserId() == null || task.getUserId().isEmpty()) {
            throw new IllegalArgumentException("Task must have a valid userId");
        }

        User user = fetchUserById(task.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("User not found for userId: " + task.getUserId());
        }

        Task createdTask = taskService.createTask(task, user);
        return ResponseEntity.status(201).body(createdTask);
    }

    // Get tasks based on the authenticated user's role
    @GetMapping
    public ResponseEntity<List<Task>> getTasks(@AuthenticationPrincipal User authenticatedUser) {
        logger.info("Fetching tasks for authenticated userId: {}", authenticatedUser.getId());

        // Call the service to get tasks based on the user's role
        List<Task> tasks = taskService.getTasks(authenticatedUser);

        return ResponseEntity.ok(tasks);
    }

    // Simulate fetching the user (Replace with database/service call in real app)
    private User fetchUserById(String userId) {
        User user = new User();
        user.setId(userId);
        user.setName("Mock User");
        user.setCompanyId("company123");
        user.setType(UserType.STANDARD); // Adjust user type for testing
        return user;
    }
}
