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
}
