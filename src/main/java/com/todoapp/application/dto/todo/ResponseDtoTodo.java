package com.todoapp.application.dto.todo;

import com.todoapp.application.dto.BaseResponseDto;
import com.todoapp.application.dto.user.ResponseDtoUser;

public class ResponseDtoTodo extends BaseResponseDto {

    private String title;

    private String desc;

    private ResponseDtoUser user;

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

    public ResponseDtoUser getUser() {
        return user;
    }

    public void setUser(ResponseDtoUser user) {
        this.user = user;
    }

}
