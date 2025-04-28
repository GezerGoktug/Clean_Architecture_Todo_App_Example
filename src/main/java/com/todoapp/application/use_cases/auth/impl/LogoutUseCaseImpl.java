package com.todoapp.application.use_cases.auth.impl;

import com.todoapp.application.accessors.AuthenticationAccessors;
import com.todoapp.application.accessors.HttpContextAccessors;
import com.todoapp.application.dto.base.ResponseDtoDefault;
import com.todoapp.application.use_cases.auth.ILogoutUseCase;

public class LogoutUseCaseImpl implements ILogoutUseCase {

    private final HttpContextAccessors httpContextAccessors;

    private final AuthenticationAccessors authenticationManager;

    public LogoutUseCaseImpl(AuthenticationAccessors authenticationAccessors,
            HttpContextAccessors httpContextAccessors) {
        this.authenticationManager = authenticationAccessors;
        this.httpContextAccessors = httpContextAccessors;
    }

    private ResponseDtoDefault toResponseDTO(String message) {
        ResponseDtoDefault responseDtoDefault = new ResponseDtoDefault();

        responseDtoDefault.setMessage(message);

        return responseDtoDefault;
    }

    @Override
    public ResponseDtoDefault execute() {

        httpContextAccessors.clearCookie("refreshToken");

        authenticationManager.clearAuthentication();

        httpContextAccessors.clearSession();

        return toResponseDTO("Başarıyla çıkış yapılmıştır");
    }
}
