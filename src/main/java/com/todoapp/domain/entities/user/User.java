package com.todoapp.domain.entities.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.todoapp.domain.entities.BaseEntity;
import com.todoapp.domain.enums.Role;

public class User extends BaseEntity {

    private String username;

    private String email;

    private String password;

    private Role role;

    @Override
    public String toString() {
        return "User [username=" + username + ", email=" + email + ", role=" + role + "]";
    }

    public User(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    private List<String> isValidPassword(String password) {
        List<String> messages = new ArrayList<>();
        if (password == null || password.isEmpty())
            messages.add("Şifre boş olamaz");
        if (password.length() < 8)
            messages.add("Şifre en az 8 karakter olmalıdır");

        long letterCount = password.chars().filter(Character::isLetter).count();

        if (letterCount < 4)
            messages.add("Şifrede en az 4 harf olmalıdır");

        return messages;
    }

    private List<String> isValidEmail(String email) {
        List<String> messages = new ArrayList<>();
        if (email.isEmpty()) {
            messages.add("Email boş geçilemez");
        }
        if (!email.matches("^(.+)@(\\S+\\.\\S+)$")) {
            messages.add("Email uygun formatta değil");
        }

        return messages;
    }

    public HashMap<String, Object> validateUser() {
        HashMap<String, Object> errorMap = new HashMap<>();

        if (username.trim().length() < 3) {
            errorMap.put("username", "Kullanıcı adı 3 karakterden aşağı olamaz");
        }

        List<String> emailMessages = isValidEmail(email);
        if (emailMessages.size() > 0)
            errorMap.put("email", emailMessages);

        List<String> messages = isValidPassword(password);
        if (messages.size() > 0)
            errorMap.put("password", messages);

        if (role == null)
            errorMap.put("role", "Rol boş olamaz");

        return errorMap;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
