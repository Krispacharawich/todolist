package com.backend.todolist.controller;

import com.backend.todolist.model.Task;
import com.backend.todolist.model.request.AddTaskRequest;
import com.backend.todolist.model.request.UpdatePrioritizedTaskRequest;
import com.backend.todolist.model.response.TaskListResponse;
import com.backend.todolist.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/")
    public @ResponseBody TaskListResponse getAllTask() {
        return taskService.getAllTasks();
    }

    @PostMapping("/add")
    public @ResponseBody Task addTask(@RequestBody @Valid  AddTaskRequest addTaskRequest) throws BadRequestException {
        return taskService.addTask(addTaskRequest);
    }

    @PostMapping("/{id}/toggle")
    public void toggleStatus(@PathVariable("id") Integer id) {
        taskService.toggleStatus(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Integer id) {
        taskService.deleteTaskById(id);
    }

    @PostMapping("/priority/move-up")
    public void prioritizeMoveUpTasks(@RequestBody UpdatePrioritizedTaskRequest updatePrioritizedTaskRequest) {
        taskService.prioritizeMoveUpTasks(updatePrioritizedTaskRequest);
    }

    @PostMapping("/priority/move-down")
    public void prioritizeMoveDownTasks(@RequestBody UpdatePrioritizedTaskRequest updatePrioritizedTaskRequest) {
        taskService.prioritizeMoveDownTasks(updatePrioritizedTaskRequest);
    }

}
