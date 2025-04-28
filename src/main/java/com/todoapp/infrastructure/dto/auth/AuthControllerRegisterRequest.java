package com.todoapp.infrastructure.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthControllerRegisterRequest {

    @NotEmpty(message = "Mail alanı boş olamaz")
    @Email(message = "Mail doğru formatta değil")
    private String email;

    @NotEmpty(message = "Şifre alanı boş olamaz")
    @Size(min = 8, message = "Şifre alanı 8 karakterden az olamaz")
    @Pattern(regexp = "^(?=(?:.*[a-zA-Z]){4}).*$", message = "Şifre en az 4 harf içermelidir")
    private String password;

    @NotEmpty(message = "Username alanı boş olamaz")
    @Size(min = 3, message = "Kullanıcı adı en az 3 karakter olmalıdır")
    private String username;
}
