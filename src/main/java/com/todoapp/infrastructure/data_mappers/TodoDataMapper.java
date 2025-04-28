package com.todoapp.infrastructure.data_mappers;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "todo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoDataMapper extends BaseDataMapper {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String desc;

    @ManyToOne
    private UserDataMapper user;

}
