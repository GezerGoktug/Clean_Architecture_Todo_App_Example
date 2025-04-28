package com.todoapp.application.ds_gateways.dto;

import java.util.Date;

public abstract class BaseDataRequestDTO {

    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
