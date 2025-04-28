package com.todoapp.application.use_cases.auth;

import java.util.Map;

public interface IOAuthLoginUseCase {
    String execute(Map<String, Object> userAttributes);
}
