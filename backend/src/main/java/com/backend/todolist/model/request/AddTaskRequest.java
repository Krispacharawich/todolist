package com.backend.todolist.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTaskRequest {
    @NotBlank(message = "title is required")
    private String title;
}
