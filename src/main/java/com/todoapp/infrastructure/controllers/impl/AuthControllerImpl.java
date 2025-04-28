package com.todoapp.infrastructure.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.application.dto.base.ResponseDtoDefault;
import com.todoapp.application.dto.refreshToken.ResponseDtoRefreshToken;
import com.todoapp.application.dto.user.LoginRequestDtoUser;
import com.todoapp.application.dto.user.RegisterRequestDtoUser;
import com.todoapp.application.interactors.auth.AuthInputBoundary;
import com.todoapp.application.dto.user.AuthResponseDtoUser;
import com.todoapp.infrastructure.controllers.IAuthController;
import com.todoapp.infrastructure.controllers.RestBaseController;
import com.todoapp.infrastructure.controllers.RootEntity;
import com.todoapp.infrastructure.dto.auth.AuthControllerLoginRequest;
import com.todoapp.infrastructure.dto.auth.AuthControllerRegisterRequest;
import com.todoapp.infrastructure.util.DtoMapper;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth")
@CrossOrigin(origins = "http://localhost:4000")
public class AuthControllerImpl extends RestBaseController implements IAuthController {

    @Autowired
    private AuthInputBoundary authInput;

    @Override
    @PostMapping("/register")
    public RootEntity<AuthResponseDtoUser> register(@Valid @RequestBody AuthControllerRegisterRequest request) {
        return ok(authInput.register(DtoMapper.map(request, RegisterRequestDtoUser.class)), 200);
    }

    @Override
    @PostMapping("/refreshToken")
    public RootEntity<ResponseDtoRefreshToken> refreshToken() {
        return ok(authInput.refreshToken(), 200);
    }

    @Override
    @PostMapping("/logout")
    public RootEntity<ResponseDtoDefault> logout() {
        return ok(authInput.logout(), 200);
    }

    @Override
    @PostMapping("/login")
    public RootEntity<AuthResponseDtoUser> login(@Valid @RequestBody AuthControllerLoginRequest request) {
        return ok(authInput.login(DtoMapper.map(request, LoginRequestDtoUser.class)), 200);
    }

}
