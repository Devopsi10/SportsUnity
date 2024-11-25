package com.todoapp.service;

import com.todoapp.model.Task;
import com.todoapp.model.User;
import com.todoapp.model.UserType;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final Map<String, Task> taskRepository = new HashMap<>();
    private final Map<String, User> userRepository = new HashMap<>(); // Mock user database

    public TaskService() {
        // Initialize mock user data (for testing purposes)
        userRepository.put("user1", new User("user1", "Alice", "company1", UserType.STANDARD));
        userRepository.put("admin1", new User("admin1", "Bob", "company1", UserType.COMPANY_ADMIN));
        userRepository.put("super1", new User("super1", "Charlie", "N/A", UserType.SUPER_USER));
    }

    // Create a task and assign it to a user
    public Task createTask(Task task, User user) {
        task.setId(UUID.randomUUID().toString());
        task.setUserId(user.getId());
        task.setCompanyId(user.getCompanyId()); // Assign task to user's company
        taskRepository.put(task.getId(), task);
        return task;
    }

    // Get tasks based on user role
    public List<Task> getTasks(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must be authenticated.");
        }

        switch (user.getType()) {
            case SUPER_USER:
                return new ArrayList<>(taskRepository.values()); // Super Users see all tasks

            case COMPANY_ADMIN:
                // Company Admins see all tasks in their company
                return taskRepository.values().stream()
                        .filter(task -> task.getCompanyId().equals(user.getCompanyId()))
                        .collect(Collectors.toList());

            case STANDARD:
                // Standard Users see only their tasks
                return taskRepository.values().stream()
                        .filter(task -> task.getUserId().equals(user.getId()))
                        .collect(Collectors.toList());

            default:
                throw new UnsupportedOperationException("Unhandled user type: " + user.getType());
        }
    }

    // Fetch a user by their ID (This method should ideally be replaced with a real database lookup)
    public User getUserById(String userId) {
        return userRepository.get(userId);
    }

    // For testing purposes, add a task to the repository directly
    public void addTaskForTesting(Task task) {
        taskRepository.put(task.getId(), task);
    }
}
