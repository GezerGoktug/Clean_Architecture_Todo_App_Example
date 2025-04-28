package com.todoapp.application.ds_gateways.dto;

public class TodoDataRequestDTO extends BaseDataRequestDTO {

    private String title;

    private String desc;

    private TodoUserDataRequestDTO user;

    public TodoDataRequestDTO() {

    }

    public TodoDataRequestDTO(String title, String desc, TodoUserDataRequestDTO user) {
        this.title = title;
        this.desc = desc;
        this.user = user;
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

    public TodoUserDataRequestDTO getUser() {
        return user;
    }

    public void setUser(TodoUserDataRequestDTO user) {
        this.user = user;
    }

}
