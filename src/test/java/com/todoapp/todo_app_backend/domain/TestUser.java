package com.todoapp.todo_app_backend.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.todoapp.domain.entities.user.User;

public class TestUser {

    @Test
    void shouldBeUserValidForm() {
        User user = new User("", "", "", null);

        HashMap<String, Object> errorMap = user.validateUser();

        assertEquals(4, errorMap.size());

        assertEquals(2, ((List<String>) errorMap.get("email")).size());
        
        List<String> expectedEmailErrorMessages = new ArrayList<>();

        expectedEmailErrorMessages.add("Email boş geçilemez");
        expectedEmailErrorMessages.add("Email uygun formatta değil");

        assertLinesMatch(expectedEmailErrorMessages, ((List<String>) errorMap.get("email")));

    }

}
