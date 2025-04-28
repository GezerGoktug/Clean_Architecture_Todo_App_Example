package com.todoapp.application.ds_gateways.dto;

import java.util.Date;
import java.util.UUID;

public abstract class BaseDataResponseDTO {

    private Date createdAt;

    private UUID id;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
