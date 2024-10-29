package com.backend.TodoList.model.response;

import com.backend.TodoList.model.Task;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TaskListResponse {
    private List<Task> taskList;
}
