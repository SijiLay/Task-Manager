package com.codewithsiji.taskmanagerapi.task;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping
    public List<Task> findAll() {return taskService.getTasks();}

    @GetMapping("/{id}")
    public Task findOne(@PathVariable Long id) {return taskService.getTask(id);}

    @PostMapping
    public ResponseEntity<Task> save(@Valid @RequestBody TaskRequest request){
        Task createdTask = taskService.addTask(request);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public Task deleteTask(@PathVariable Long id){ return taskService.removeTask(id);}

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest request){return taskService.updateTask(id, request);}

    @GetMapping("/filter/category")
    public List<Task> filterByCategory(@RequestParam Category category){return taskService.getTaskByCategory(category);}

    @GetMapping("/filter/priority")
    public List<Task> filterByPriority(@RequestParam Priority priority){return taskService.getTaskByPriority(priority);}

    @GetMapping("/filter")
    public List<Task> filterByPriorityAndCategory(@RequestParam Category category,@RequestParam Priority priority){return taskService.getTaskByCategoryAndPriority(category,priority);}

    @GetMapping("/filter/completed")
    public List<Task> filterByCompleted(@RequestParam boolean completed){return taskService.getTaskByCompleted(completed);}

    @PutMapping("/{id}/complete")
    public Task completeTask(@PathVariable Long id){return taskService.completeTask(id);}

    @PutMapping("/{id}/incomplete")
    public Task incompleteTask(@PathVariable long id){
        return taskService.markTaskIncomplete(id);
    }
}
