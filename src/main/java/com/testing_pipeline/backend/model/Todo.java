package com.testing_pipeline.backend.model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
public class Todo {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    @Column(name = "category", nullable = false)
    private String category;
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    public Todo(String title, Priority priority) {
        this.title = title;
        this.priority = priority;
    }

    public Todo() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
