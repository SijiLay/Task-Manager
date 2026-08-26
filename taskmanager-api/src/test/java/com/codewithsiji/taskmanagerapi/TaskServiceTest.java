package com.codewithsiji.taskmanagerapi;
import com.codewithsiji.taskmanagerapi.task.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;
    @Mock
    private TaskRepository taskRepository;

    @Test
    void getTaskReturnsTask() {
        Task task = new Task("Study Java", false, Priority.HIGH, Category.SCHOOL );

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task taskReturned = taskService.getTask(1L);
        assertEquals(task, taskReturned);
    }

    @Test
    void getTaskThrowsExceptionWhenTaskNotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(99L));
    }

    @Test
    void updatedTaskCompletionReturnsCompletedTask() {
        Task task = new Task("Study Java", false, Priority.HIGH, Category.SCHOOL );

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task completedTask = taskService.completeTask(1L);

        assertTrue(completedTask.isCompleted());
        verify(taskRepository).save(task);
    }

    @Test
    void updatedTaskCompletionReturnsIncompletedTask() {
        Task task = new Task("Study Java", true, Priority.HIGH, Category.SCHOOL );

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task incompleteTask = taskService.markTaskIncomplete(1L);

        assertFalse(incompleteTask.isCompleted());
        verify(taskRepository).save(task);
    }

    @Test
    void addedTaskStartsWithFalseCompletion(){
        TaskRequest request = new TaskRequest();
        request.setName("Study Java");
        request.setPriority(Priority.HIGH);
        request.setCategory(Category.SCHOOL);

        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArgument(0)); //Whatever Task the service passes into save(), just return that same Task back.

        Task addedTask = taskService.addTask(request);
        assertFalse(addedTask.isCompleted());
    }

    @Test
    void removeTaskReturnsRemovedTask(){
        Task task = new Task(
                "Study Java",
                false,
                Priority.HIGH,
                Category.SCHOOL
        );

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Task removedTask = taskService.removeTask(1L);
        assertEquals(task, removedTask);
        verify(taskRepository).delete(task);
    }

    @Test
    void updateTaskReturnsUpdatedTask(){
        Task existingTask = new Task(
                "Study Java",
                false,
                Priority.HIGH,
                Category.SCHOOL
        );

        TaskRequest request = new TaskRequest();
        request.setName("Study Spring Boot");
        request.setPriority(Priority.MEDIUM);
        request.setCategory(Category.PERSONAL);


        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        Task updatedTask = taskService.updateTask(1L, request);
        assertEquals("Study Spring Boot", updatedTask.getName());
        assertEquals(Priority.MEDIUM, updatedTask.getPriority());
        assertEquals(Category.PERSONAL, updatedTask.getCategory());

        verify(taskRepository).save(existingTask);
    }

    @Test
    void getTasksReturnsAllTasks(){
        List<Task> tasks = List.of(
                new Task("Study Java", false, Priority.HIGH, Category.SCHOOL),
                new Task("Go to gym", true, Priority.MEDIUM, Category.FITNESS)
        );

        when(taskRepository.findAll()).thenReturn(tasks);
        List<Task> returnedTasks = taskService.getTasks();

        assertEquals(tasks, returnedTasks);
    }

    @Test
    void getTasksByCategoryReturnsMatchingTasks(){
        List<Task> tasks = List.of(
                new Task("Study Java", false, Priority.HIGH, Category.SCHOOL),
                new Task("Study Spring Boot", true, Priority.MEDIUM, Category.SCHOOL)
        );

        when(taskRepository.findByCategory(Category.SCHOOL)).thenReturn(tasks);
        List<Task> returnedTasks  = taskService.getTaskByCategory(Category.SCHOOL);
        assertEquals(tasks, returnedTasks);
    }

    @Test
    void getTasksByPriorityReturnsMatchingTasks(){
        List<Task> tasks = List.of(
                new Task("Study Java", false, Priority.HIGH, Category.SCHOOL),
                new Task("Finish Project", true, Priority.HIGH, Category.WORK)
        );

        when(taskRepository.findByPriority(Priority.HIGH)).thenReturn(tasks);
        List<Task> returnedTasks  = taskService.getTaskByPriority(Priority.HIGH);
        assertEquals(tasks, returnedTasks);
    }

    @Test
    void getTasksByNameReturnsMatchingTasks(){
        List<Task> tasks = List.of(
                new Task("Study Java", true, Priority.HIGH, Category.SCHOOL),
                new Task("Go to gym", true, Priority.MEDIUM, Category.FITNESS)
        );

        when(taskRepository.findByCompleted(true)).thenReturn(tasks);
        List<Task> returnedTasks = taskService.getTaskByCompleted(true);
        assertEquals(tasks, returnedTasks);
    }

    @Test
    void getTasksByCategoryAndPriorityReturnsMatchingTasks(){
        List<Task> tasks = List.of(
                new Task("Study Java", false, Priority.HIGH, Category.SCHOOL),
                new Task("Study Spring Boot", true, Priority.HIGH, Category.SCHOOL)
        );

        when(taskRepository.findByCategoryAndPriority(Category.SCHOOL,Priority.HIGH)).thenReturn(tasks);
        List<Task> returnedTask = taskService.getTaskByCategoryAndPriority(Category.SCHOOL,Priority.HIGH);
        assertEquals(tasks, returnedTask);
    }
}
