package com.todoapp.infrastructure.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthControllerLoginRequest {

    @NotEmpty(message = "Mail alanı boş olamaz")
    @Email(message = "Mail doğru formatta değil")
    private String email;

    @NotEmpty(message = "Şifre alanı boş olamaz")
    private String password;
}
