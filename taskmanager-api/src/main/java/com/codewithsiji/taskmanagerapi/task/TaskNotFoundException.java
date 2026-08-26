package com.codewithsiji.taskmanagerapi.task;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException(Long id){
        super("Task not found with ID: "+id);
    }
}
