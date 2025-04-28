package com.todoapp.application.interactors.auth;

import java.util.Map;

import com.todoapp.application.dto.base.ResponseDtoDefault;
import com.todoapp.application.dto.refreshToken.ResponseDtoRefreshToken;
import com.todoapp.application.dto.user.LoginRequestDtoUser;
import com.todoapp.application.dto.user.RegisterRequestDtoUser;
import com.todoapp.application.dto.user.AuthResponseDtoUser;

public interface AuthInputBoundary {

    AuthResponseDtoUser register(RegisterRequestDtoUser dtoUser);

    ResponseDtoRefreshToken refreshToken();

    ResponseDtoDefault logout();

    AuthResponseDtoUser login(LoginRequestDtoUser dtoUser);

    String oauth2Login(Map<String, Object> userAttributes);
}
