package com.backend.TodoList.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTaskRequest {
    @NotBlank(message = "title is required")
    private String title;
}
