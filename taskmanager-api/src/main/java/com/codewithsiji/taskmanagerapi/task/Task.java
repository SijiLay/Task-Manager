package com.codewithsiji.taskmanagerapi.task;


import jakarta.persistence.*;

@Entity
public class Task {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private boolean completed;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Category category;


    public Task() {
    }

    public Task(String name, boolean completed, Priority priority, Category category) {
        this.name = name;
        this.completed = completed;
        this.priority = priority;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
