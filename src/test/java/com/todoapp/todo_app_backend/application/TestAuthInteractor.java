package com.todoapp.todo_app_backend.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.todoapp.application.accessors.AuthenticationAccessors;
import com.todoapp.application.accessors.HttpContextAccessors;
import com.todoapp.application.ds_gateways.UserDsGateway;
import com.todoapp.application.ds_gateways.dto.UserDataResponseDTO;
import com.todoapp.application.dto.user.AuthResponseDtoUser;
import com.todoapp.application.dto.user.RegisterRequestDtoUser;
import com.todoapp.application.exceptions.BaseException;
import com.todoapp.application.interactors.token.TokenInputBoundary;
import com.todoapp.application.use_cases.auth.impl.RegisterUserUseCaseImpl;
import com.todoapp.domain.entities.user.User;
import com.todoapp.domain.enums.Role;
import com.todoapp.domain.factory.UserFactory.IUserFactory;

public class TestAuthInteractor {

    private UserDsGateway userDsGateway;
    private IUserFactory userFactory;
    private TokenInputBoundary tokenInput;
    private AuthenticationAccessors authenticationAccessors;
    private HttpContextAccessors httpContextAccessors;
    private RegisterUserUseCaseImpl registerUserUseCase;

    // ! Bağımlılıkları mock'luyoruz.
    @BeforeEach
    void setUp() {
        userDsGateway = mock(UserDsGateway.class);
        userFactory = mock(IUserFactory.class);
        tokenInput = mock(TokenInputBoundary.class);
        authenticationAccessors = mock(AuthenticationAccessors.class);
        httpContextAccessors = mock(HttpContextAccessors.class);

        // ! Use Case de dependency injection sağlanmalı

        registerUserUseCase = new RegisterUserUseCaseImpl(
                userDsGateway, userFactory, tokenInput, authenticationAccessors, httpContextAccessors);
    }

    // ! 3 -> AAA (Arrange Act Assert)
    @Test
    void shouldBeTestRegisterSuccessfully() {
        // ? ARRANGE

        RegisterRequestDtoUser request = new RegisterRequestDtoUser();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password123");

        // Kullanıcı bulunamadı (Çünkü db de yok)
        when(userDsGateway.existUserByEmail(request.getEmail())).thenReturn(false);

        User user = new User(request.getUsername(), request.getEmail(), request.getPassword(), Role.USER);
        when(userFactory.create(anyString(), anyString(), anyString(), any(Role.class))).thenReturn(user);

        when(authenticationAccessors.hashPassword(anyString())).thenReturn("hashedPassword");

        // User kaydediliyor ve DTO dönülüyor
        UserDataResponseDTO userDataResponse = new UserDataResponseDTO();
        userDataResponse.setUsername("testuser");
        userDataResponse.setEmail("test@example.com");
        userDataResponse.setRole("USER");
        userDataResponse.setId(UUID.randomUUID());
        when(userDsGateway.saveUser(any())).thenReturn(userDataResponse);

        when(tokenInput.generateAccessToken(anyString(), anyString(), anyString(), anyString()))
                .thenReturn("accessTokenExample");
        when(tokenInput.generateRefreshToken(anyString(), anyString(), anyString(), anyString()))
                .thenReturn("refreshTokenExample");

        AuthResponseDtoUser response = registerUserUseCase.execute(request, false);

        assertNotNull(response);
        assertEquals("testuser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("accessTokenExample", response.getAccessToken());
    }

    @Test
    void shouldBeTestRegisterNotSuccessfullyBecauseEmailAlreadyExist() {
        // Arrange
        RegisterRequestDtoUser request = new RegisterRequestDtoUser();
        request.setUsername("testuser");
        request.setEmail("existing@example.com");
        request.setPassword("password123");

        // Kullanıcı zaten var
        when(userDsGateway.existUserByEmail(request.getEmail())).thenReturn(true);

        // Act & Assert
        BaseException exception = assertThrows(BaseException.class, () -> {
            registerUserUseCase.execute(request, false);
        });

        assertEquals(404, exception.getStatusCode());
        System.out.println(exception.getMessage());
        assertEquals("Email already used : existing@example.com", exception.getMessage());
    }

}
