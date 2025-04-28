package com.todoapp.domain.entities.todo;

import java.util.HashMap;

import com.todoapp.domain.entities.BaseEntity;
import com.todoapp.domain.entities.user.AppUser;

public class Todo extends BaseEntity {

    private String title;

    private String desc;

    private AppUser user;

    public Todo(String title, String desc, AppUser user) {
        this.title = title;
        this.desc = desc;
        this.user = user;
    }

    public HashMap<String, Object> validateTodo() {
        HashMap<String, Object> errorMap = new HashMap<>();

        if (title.length() < 3) {
            errorMap.put("title", "Title alanı 3 karakterden aşağı olamaz");
        }

        if (desc.length() < 3) {
            errorMap.put("description", "Description alanı 3 karakterden aşağı olamaz");
        }

        if (user == null) {
            errorMap.put("user", "Her bir todo için bir kullanıcı olmalı");
        }        

        return errorMap;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public AppUser getUser() {
        return user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

}
