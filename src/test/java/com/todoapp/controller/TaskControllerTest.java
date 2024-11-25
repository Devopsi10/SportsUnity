package com.todoapp;

import com.todoapp.controller.TaskController;
import com.todoapp.model.Task;
import com.todoapp.model.User;
import com.todoapp.model.UserType;
import com.todoapp.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)  // Mockito setup without Spring Boot test context
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    private Task task;
    private User standardUser;
    private User companyAdmin;
    private User superUser;

    @BeforeEach
    public void setUp() {
        task = new Task();
        task.setDescription("Test Task");
        task.setCompleted(false);
        task.setUserId("user123");

        // Create users for each type
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

        // Initialize MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testCreateTaskForStandardUser() throws Exception {
        when(taskService.createTask(task, standardUser)).thenReturn(task);

        mockMvc.perform(post("/tasks")
                .contentType("application/json")
                .content("{ \"description\": \"Test Task\", \"completed\": false, \"userId\": \"user123\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Test Task"))
                .andExpect(jsonPath("$.userId").value("user123"));
    }

    @Test
    public void testGetTasksForStandardUser() throws Exception {
        when(taskService.getTasks(standardUser)).thenReturn(List.of(task));

        mockMvc.perform(get("/tasks?userId=user123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Test Task"));
    }

    @Test
    public void testGetTasksForCompanyAdmin() throws Exception {
        Task taskForAdmin = new Task();
        taskForAdmin.setDescription("Admin Task 1");
        taskForAdmin.setUserId("user123");
        taskForAdmin.setCompanyId("company1");

        when(taskService.getTasks(companyAdmin)).thenReturn(List.of(taskForAdmin));

        mockMvc.perform(get("/tasks?userId=admin1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Admin Task 1"));
    }

    @Test
    public void testGetTasksForSuperUser() throws Exception {
        Task taskForSuperUser = new Task();
        taskForSuperUser.setDescription("Super User Task");
        taskForSuperUser.setUserId("user123");

        when(taskService.getTasks(superUser)).thenReturn(List.of(taskForSuperUser));

        mockMvc.perform(get("/tasks?userId=superuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Super User Task"));
    }
}
