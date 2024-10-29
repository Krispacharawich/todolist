package com.backend.todolist.model.response;

import com.backend.todolist.model.Task;
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
