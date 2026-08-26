package com.codewithsiji.taskmanagerapi;

import com.codewithsiji.taskmanagerapi.task.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;


@WebMvcTest(TaskController.class)
public class TaskControllerErrorTest {
    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void blankTaskNameReturnsBadRequest() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setName("");
        request.setPriority(Priority.HIGH);
        request.setCategory(Category.SCHOOL);

        mockMvc.perform(
                        post("/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Task Name cannot be blank"));
    }

    @Test
    void missingPriorityReturnsBadRequest() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setName("Study");
        request.setPriority(null);
        request.setCategory(Category.SCHOOL);

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.priority").value("Priority is required"));
    }

    @Test
    void missingCategoryReturnsBadRequest() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setName("Study Java");
        request.setPriority(Priority.HIGH);
        request.setCategory(null);

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.category").value("Category is required"));
    }

    @Test
    void missingTaskReturnsNotFound() throws Exception {

        when(taskService.getTask(99L)).thenThrow(new TaskNotFoundException(99L));
        mockMvc.perform(get("/tasks/99"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("Task not found with ID: 99"));
    }

    @Test
    void updateWithBlankNameReturnsBadRequest() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setName("");
        request.setPriority(Priority.MEDIUM);
        request.setCategory(Category.PERSONAL);


        mockMvc.perform(put("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Task Name cannot be blank"));
    }

    @Test
    void updateWithMissingPriorityReturnsBadRequest() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setName("Updated Task");
        request.setPriority(null);
        request.setCategory(Category.PERSONAL);

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.priority").value("Priority is required"));
    }

    @Test
    void updateWithMissingCategoryReturnsBadRequest() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setName("Updated Task");
        request.setPriority(Priority.MEDIUM);
        request.setCategory(null);

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.category").value("Category is required"));
    }
}
