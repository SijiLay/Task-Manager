package com.codewithsiji.taskmanagerapi.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCategory(Category category);

    List<Task> findByPriority(Priority priority);

    List<Task> findByCategoryAndPriority(Category category, Priority priority);

    List<Task> findByCompleted(boolean completed);
}
