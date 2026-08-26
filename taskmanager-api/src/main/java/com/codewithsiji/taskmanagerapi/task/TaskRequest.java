package com.codewithsiji.taskmanagerapi.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskRequest {

    @NotBlank(message = "Task Name cannot be blank")
    private String name;

    @NotNull(message = "Priority is required")
    private Priority priority;

    @NotNull(message = "Category is required")
    private Category category;

    public TaskRequest() {};

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Priority getPriority() {return priority;}
    public void setPriority(Priority priority) {this.priority = priority;}

    public Category getCategory() {return category;}
    public void setCategory(Category category) {this.category = category;}
}
