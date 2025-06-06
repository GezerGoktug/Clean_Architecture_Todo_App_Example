package com.todoapp.application.dto.todo;

public class RequestDtoTodo {

    private String title;

    private String desc;

    public RequestDtoTodo() {
    }

    public RequestDtoTodo(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
