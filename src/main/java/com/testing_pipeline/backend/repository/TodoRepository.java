package com.testing_pipeline.backend.repository;

import com.testing_pipeline.backend.model.Priority;
import com.testing_pipeline.backend.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    public List<Todo> findTodoByPriority(Priority priority);
}
