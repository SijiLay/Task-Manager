package com.codewithsiji.taskmanagerapi;

import com.codewithsiji.taskmanagerapi.task.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;


import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Test
    void getTaskReturnsOk() throws Exception {
        List<Task> tasks = List.of(
                new Task("Study Spring Boot", false, Priority.HIGH, Category.SCHOOL),
                new Task("Go to the gym", true, Priority.MEDIUM, Category.FITNESS)
        );

        when(taskService.getTasks()).thenReturn(tasks);
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Study Spring Boot"))
                .andExpect(jsonPath("$[1].category").value("FITNESS"));

    }

    @Test
    void getTaskByIdReturnsTask() throws Exception {
        Task task = new Task(
                "Study Spring Boot",
                false,
                Priority.HIGH,
                Category.SCHOOL
        );

        when(taskService.getTask(1L)).thenReturn(task);

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Study Spring Boot"))
                .andExpect(jsonPath("$.completed").value(false))
                .andExpect(jsonPath("$.category").value("SCHOOL"))
                .andExpect(jsonPath("$.priority").value("HIGH"));
    }

    @Test
    void postTaskReturnsCreatedTask() throws Exception {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("Study Spring Boot");
        taskRequest.setCategory(Category.SCHOOL);
        taskRequest.setPriority(Priority.HIGH);

        Task createdTask = new Task( "Study Spring Boot", false, Priority.HIGH, Category.SCHOOL);

        when(taskService.addTask(any(TaskRequest.class))).thenReturn(createdTask);
        mockMvc.perform(post("/tasks").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Study Spring Boot"))
                .andExpect(jsonPath("$.completed").value(false))
                .andExpect(jsonPath("$.category").value("SCHOOL"))
                .andExpect(jsonPath("$.priority").value("HIGH"));
    }

    @Test
    void removeTaskReturnsDeletedTask() throws Exception {
        Task removedTask = new Task( "Study Spring Boot", false, Priority.HIGH, Category.SCHOOL);

        when(taskService.removeTask(1L)).thenReturn(removedTask);

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Study Spring Boot"))
                .andExpect(jsonPath("$.completed").value(false))
                .andExpect(jsonPath("$.category").value("SCHOOL"))
                .andExpect(jsonPath("$.priority").value("HIGH"));
    }

    @Test
    void updateTaskReturnsUpdatedTask() throws Exception {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("Finish Spring Boot");
        taskRequest.setCategory(Category.SCHOOL);
        taskRequest.setPriority(Priority.MEDIUM);

        Task updatedTask = new Task(
                "Finish Spring Boot",
                false,
                Priority.MEDIUM,
                Category.SCHOOL
        );

        when(taskService.updateTask(any(Long.class), any(TaskRequest.class)))
                .thenReturn(updatedTask);

        mockMvc.perform(
                        put("/tasks/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(taskRequest))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Finish Spring Boot"))
                .andExpect(jsonPath("$.completed").value(false))
                .andExpect(jsonPath("$.category").value("SCHOOL"))
                .andExpect(jsonPath("$.priority").value("MEDIUM"));
    }

    @Test
    void getTasksFilteredByCategoryReturnsTasks() throws Exception {
        List<Task> tasks = List.of(
                new Task("Study Spring Boot", false, Priority.HIGH, Category.SCHOOL),
                new Task("Study Java FX", true, Priority.MEDIUM, Category.SCHOOL)
        );

        when(taskService.getTaskByCategory(Category.SCHOOL)).thenReturn(tasks);

        mockMvc.perform(get("/tasks/filter/category?category=SCHOOL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("SCHOOL"))
                .andExpect(jsonPath("$[1].category").value("SCHOOL"));
    }

    @Test
    void getTasksFilteredByPriorityReturnsTasks() throws Exception {
        List<Task> tasks = List.of(
                new Task("Study Spring Boot", false, Priority.HIGH, Category.SCHOOL),
                new Task("Study Java FX", true, Priority.HIGH, Category.SCHOOL)
        );

        when(taskService.getTaskByPriority(Priority.HIGH)).thenReturn(tasks);

        mockMvc.perform(get("/tasks/filter/priority?priority=HIGH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].priority").value("HIGH"))
                .andExpect(jsonPath("$[1].priority").value("HIGH"));
    }

    @Test
    void getTasksFilteredByCategoryAndPriorityReturnsTasks() throws Exception {
        List<Task> tasks = List.of(
                new Task("Study Spring Boot", false, Priority.HIGH, Category.SCHOOL),
                new Task("Study Java FX", true, Priority.HIGH, Category.SCHOOL)
        );

        when(taskService.getTaskByCategoryAndPriority(Category.SCHOOL, Priority.HIGH)).thenReturn(tasks);

        mockMvc.perform(get("/tasks/filter?category=SCHOOL&priority=HIGH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("SCHOOL"))
                .andExpect(jsonPath("$[1].category").value("SCHOOL"))
                .andExpect(jsonPath("$[0].priority").value("HIGH"))
                .andExpect(jsonPath("$[1].priority").value("HIGH"));
    }

    @Test
    void  getTasksFilteredByCompletedReturnsTasks() throws Exception {
        List<Task> tasks = List.of(
                new Task("Study Spring Boot", true, Priority.HIGH, Category.SCHOOL),
                new Task("Study Java FX", true, Priority.HIGH, Category.SCHOOL)
        );

        when(taskService.getTaskByCompleted(true)).thenReturn(tasks);

        mockMvc.perform(get("/tasks/filter/completed?completed=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].completed").value(true))
                .andExpect(jsonPath("$[1].completed").value(true));
    }

    @Test
    void putTaskToCompleteReturnsUpdatedTask() throws Exception {
        Task completedTask = new Task(
                "Study Spring Boot",
                true,
                Priority.HIGH,
                Category.SCHOOL
        );

        when(taskService.completeTask(1L)).thenReturn(completedTask);

        mockMvc.perform(put("/tasks/1/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
    }


    @Test
    void putTaskToIncompleteReturnsUpdatedTask() throws Exception {
        Task incompleteTask = new Task(
                "Study Spring Boot",
                false,
                Priority.HIGH,
                Category.SCHOOL
        );

        when(taskService.markTaskIncomplete(1L)).thenReturn(incompleteTask);

        mockMvc.perform(put("/tasks/1/incomplete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(false));
    }
}
