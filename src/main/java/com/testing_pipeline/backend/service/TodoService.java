package com.testing_pipeline.backend.service;

import com.testing_pipeline.backend.model.Priority;
import com.testing_pipeline.backend.model.Todo;
import com.testing_pipeline.backend.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    public List<Todo> getByPriority(Priority priority) {
        return todoRepository.findTodoByPriority(priority);
    }

    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo update(Long id, Todo todo) {
        if (!(todoRepository.existsById(id))) {
            throw new RuntimeException("Could not find Todo with id:" + id);
        }

        todo.setId(id);
        return todoRepository.save(todo);
    }

    public void delete(Long id) {
        if (!(todoRepository.existsById(id))) {
            throw new RuntimeException("Could not find Todo with id:" + id);
        }

        todoRepository.deleteById(id);
    }

}
