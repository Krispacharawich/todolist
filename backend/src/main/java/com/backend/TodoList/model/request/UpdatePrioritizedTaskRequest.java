package com.backend.TodoList.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePrioritizedTaskRequest {
    @NotNull(message = "id is required")
    private Integer id;
    @NotNull(message = "fromPriority is required")
    private Integer fromPriority;
    @NotNull(message = "toPriority is required")
    private Integer toPriority;
}
