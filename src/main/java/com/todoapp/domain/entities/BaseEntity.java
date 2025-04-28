package com.todoapp.domain.entities;

import java.util.Date;

public abstract class BaseEntity {

    private Date createdAt;

    public BaseEntity() {
        this.createdAt = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
