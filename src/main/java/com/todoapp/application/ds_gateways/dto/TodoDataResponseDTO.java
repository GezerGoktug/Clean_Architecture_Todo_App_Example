package com.todoapp.application.ds_gateways.dto;

public class TodoDataResponseDTO extends BaseDataResponseDTO {

    private String title;

    private String desc;

    private UserDataResponseDTO user;

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

    public UserDataResponseDTO getUser() {
        return user;
    }

    public void setUser(UserDataResponseDTO user) {
        this.user = user;
    }

}
