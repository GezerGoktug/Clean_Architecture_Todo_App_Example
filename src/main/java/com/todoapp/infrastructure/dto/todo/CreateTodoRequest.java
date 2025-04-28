package com.todoapp.infrastructure.dto.todo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTodoRequest {
    
    @NotEmpty(message = "Title alanı boş olamaz")
    @Size(min = 3,message = "Title alanı 3 karakterden az olamaz.")
    private String title;

    @NotEmpty(message = "Description alanı boş olamaz")
    @Size(min = 3,message = "Description alanı 3 karakter aşağısında olmamalıdır.")
    private String desc;

}
