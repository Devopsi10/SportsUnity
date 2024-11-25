package com.todoapp.service;

import com.todoapp.model.Task;
import com.todoapp.model.User;
import com.todoapp.model.UserType;
import com.todoapp.repository.TaskRepository; // Assuming you have a repository for Task
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Ensure Mockito is initialized
public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService; // This will inject the mock taskRepository

    @Mock
    private TaskRepository taskRepository; // Mock the repository or any other dependency

    private User standardUser;
    private User companyAdmin;
    private User superUser;

    @BeforeEach
    public void setUp() {
        // Create mock users for testing
        standardUser = new User();
        standardUser.setId("user123");
        standardUser.setType(UserType.STANDARD);

        companyAdmin = new User();
        companyAdmin.setId("admin1");
        companyAdmin.setType(UserType.COMPANY_ADMIN);
        companyAdmin.setCompanyId("company1");

        superUser = new User();
        superUser.setId("superuser");
        superUser.setType(UserType.SUPER_USER);
    }

    @Test
public void testGetTasksForStandardUser() {
    Task task = new Task();
    task.setDescription("Task for user123");
    task.setUserId("user123");

    // Add task directly to the repository (simulating a task being created)
    taskService.addTaskForTesting(task);

    // Test that standard user only sees their tasks
    List<Task> tasks = taskService.getTasks(standardUser);
    assertEquals(1, tasks.size());
    assertEquals("user123", tasks.get(0).getUserId());
}

@Test
public void testGetTasksForCompanyAdmin() {
    Task task = new Task();
    task.setDescription("Task for company1");
    task.setUserId("user123");
    task.setCompanyId("company1");

    // Add task to the repository for testing
    taskService.addTaskForTesting(task);

    // Test that company admin sees tasks from their company
    List<Task> tasks = taskService.getTasks(companyAdmin);
    assertEquals(1, tasks.size());
    assertEquals("company1", tasks.get(0).getCompanyId());
}

@Test
public void testGetTasksForSuperUser() {
    Task task = new Task();
    task.setDescription("Task for all users");
    task.setUserId("user123");

    // Add task to the repository for testing
    taskService.addTaskForTesting(task);

    // Test that super user sees all tasks
    List<Task> tasks = taskService.getTasks(superUser);
    assertEquals(1, tasks.size());
    assertEquals("user123", tasks.get(0).getUserId());
}

}
