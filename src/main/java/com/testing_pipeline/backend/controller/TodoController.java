package com.testing_pipeline.backend.controller;

import com.testing_pipeline.backend.model.Priority;
import com.testing_pipeline.backend.model.Todo;
import com.testing_pipeline.backend.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todo")
    public List<Todo> getAllTodos() {
        return todoService.getAll();
    }

    @GetMapping("/todo/{priority}")
    public List<Todo> getAllTodos(@PathVariable Priority priority) {
        return todoService.getByPriority(priority);
    }

    @PostMapping("/todo")
    public Todo addTodo(@RequestBody Todo todo) {
        return todoService.save(todo);
    }

    @PutMapping("/todo/{id}")
    public Todo updateTodo(@PathVariable("id") Long id, @RequestBody Todo todo) {
        return todoService.update(id, todo);
    }

    @DeleteMapping("/todo/{id}")
    public void deleteTodo(@PathVariable("id") Long id) {
        todoService.delete(id);
    }
}
