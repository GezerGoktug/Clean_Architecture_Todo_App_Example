package com.todoapp.application.dto;

import java.util.Date;
import java.util.UUID;

public abstract class BaseResponseDto {

    private UUID id;

    private Date createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
