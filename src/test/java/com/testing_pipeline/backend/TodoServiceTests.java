package com.testing_pipeline.backend;

import com.testing_pipeline.backend.model.Priority;
import com.testing_pipeline.backend.model.Todo;
import com.testing_pipeline.backend.repository.TodoRepository;
import com.testing_pipeline.backend.service.TodoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTests {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void shouldFindAllTodos() {
        List<Todo> expectedTodos = new ArrayList<>(List.of(
                new Todo("Get groceries", false, "personal", Priority.MEDIUM, LocalDateTime.now()),
                new Todo("Call boss", true, "work", Priority.HIGH, LocalDateTime.now()),
                new Todo("Watch brainrot", false, "personal", Priority.LOW, LocalDateTime.now())
        ));

        when(todoRepository.findAll()).thenReturn(expectedTodos);

        List<Todo> employees = todoService.getAll();

        Assertions.assertEquals(expectedTodos, employees);
    }

    @Test
    void shouldAddNewTodo() {
        Todo expectedTodo = new Todo("Get groceries", false, "personal", Priority.MEDIUM, LocalDateTime.now());
        when(todoRepository.save(any(Todo.class))).thenReturn(expectedTodo);

        Todo todo = todoService.save(expectedTodo);
        Assertions.assertEquals(expectedTodo, todo);
    }

    @Test
    void shouldGetTodoByPriority() {
        List<Todo> todos = new ArrayList<>(List.of(
                new Todo("Get groceries", false, "personal", Priority.MEDIUM, LocalDateTime.now()),
                new Todo("Call boss", true, "work", Priority.MEDIUM, LocalDateTime.now()),
                new Todo("Watch brainrot", false, "personal", Priority.LOW, LocalDateTime.now())
        ));

        List<Todo> expectedTodos = todos.subList(0, 1);

        when(todoRepository.findTodoByPriority(Priority.MEDIUM)).thenReturn(expectedTodos);

        List<Todo> returnedTodos = todoService.getByPriority(Priority.MEDIUM);

        Assertions.assertFalse(returnedTodos.isEmpty());
        Assertions.assertEquals(expectedTodos, returnedTodos);
    }

    @Test
    void shouldUpdateTodo() {
        List<Todo> expectedTodos = new ArrayList<>(List.of(
                new Todo("Get groceries", false, "personal", Priority.MEDIUM, LocalDateTime.now()),
                new Todo("Call boss", true, "work", Priority.HIGH, LocalDateTime.now()),
                new Todo("Watch brainrot", false, "personal", Priority.LOW, LocalDateTime.now())
        ));

        when(todoRepository.existsById(1L)).thenReturn(true);
        when(todoRepository.save(any())).thenReturn(expectedTodos.get(0));

        Todo returnedEmployee = todoService.update(1L, new Todo("Get groceries", false, "personal", Priority.MEDIUM, LocalDateTime.now()));

        verify(todoRepository, times(1)).existsById(any());
        verify(todoRepository, times(1)).save(any());
        Assertions.assertEquals(expectedTodos.get(0), returnedEmployee);
    }

    @Test
    void shouldDeleteTodo() {
        when(todoRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(RuntimeException.class, () -> todoService.delete(1L));
    }
}
