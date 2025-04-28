package com.todoapp.todo_app_backend.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.todoapp.domain.entities.todo.Todo;

public class TestTodo {
    

    @Test
    void shouldBeTodoValidForm() {
        Todo todo = new Todo("", "", null);

        HashMap<String, Object> errorMap = todo.validateTodo();

        assertEquals(3, errorMap.size());

        assertEquals("Title alanı 3 karakterden aşağı olamaz", errorMap.get("title"));
        assertEquals("Description alanı 3 karakterden aşağı olamaz", errorMap.get("description"));
        assertEquals("Her bir todo için bir kullanıcı olmalı", errorMap.get("user"));

    }
}
