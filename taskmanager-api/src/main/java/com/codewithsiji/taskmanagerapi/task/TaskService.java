package com.codewithsiji.taskmanagerapi.task;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {this.taskRepository = taskRepository;}

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(Long id){
        return taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException(id));
    }

    public Task addTask(TaskRequest request){
        return taskRepository.save(new Task(request.getName(),false,request.getPriority(),request.getCategory()));
    }

    public Task removeTask(Long id){
        Task task = getTask(id);
        taskRepository.delete(task);
        return task;
    }

    public Task updateTask(Long id, TaskRequest request){
        Task task = getTask(id);
        task.setName(request.getName());
        task.setPriority(request.getPriority());
        task.setCategory(request.getCategory());
        return taskRepository.save(task);
    }

    public List<Task> getTaskByCategory(Category category){
        return taskRepository.findByCategory(category);
    }
    public List<Task> getTaskByPriority(Priority priority){
        return taskRepository.findByPriority(priority);
    }
    public List<Task> getTaskByCategoryAndPriority(Category category, Priority priority){return taskRepository.findByCategoryAndPriority(category,priority);}

    public List<Task> getTaskByCompleted(boolean completed){return taskRepository.findByCompleted(completed);}

    public Task completeTask(Long id){
        Task task = getTask(id);
        task.setCompleted(true);
        return taskRepository.save(task);
    }
    public Task markTaskIncomplete(long id){
        Task task = getTask(id);
        task.setCompleted(false);
        return taskRepository.save(task);
    }
}
