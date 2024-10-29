package com.backend.TodoList.service;

import com.backend.TodoList.exceptions.TaskNotFoundException;
import com.backend.TodoList.mapper.TaskMapper;
import com.backend.TodoList.model.Task;
import com.backend.TodoList.model.request.AddTaskRequest;
import com.backend.TodoList.model.request.UpdatePrioritizedTaskRequest;
import com.backend.TodoList.model.response.TaskListResponse;
import com.backend.TodoList.repository.TaskRepository;
import com.backend.TodoList.repository.entity.TaskEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public Task addTask(AddTaskRequest addTaskRequest) {
        taskRepository.insertTask(addTaskRequest.getTitle());
        TaskEntity taskEntity = taskRepository.findFirstByOrderByIdDesc();
        return taskMapper.taskEntityToTask(taskEntity);
    }

    public TaskListResponse getAllTasks() {
        List<TaskEntity> taskEntityEntityList = taskRepository.findAllByOrderByPriorityAsc();
        List<Task> taskList = taskEntityEntityList.stream().map(taskMapper::taskEntityToTask)
                .toList();
        return TaskListResponse.builder().taskList(taskList).build();
    }

    public void deleteTaskById(Integer id) {
        validateExistedItem(id);
        taskRepository.updateOtherTasksPriorityForRemove(id);
        taskRepository.deleteById(id);
    }

    public void prioritizeMoveUpTasks(UpdatePrioritizedTaskRequest request) {
        validateExistedItem(request.getId());
        taskRepository.updateOtherTasksPriorityForMoveUp(request.getFromPriority(), request.getToPriority());
        taskRepository.updateTaskPriority(request.getId(), request.getToPriority());
    }

    public void prioritizeMoveDownTasks(UpdatePrioritizedTaskRequest request) {
        validateExistedItem(request.getId());
        taskRepository.updateOtherTasksPriorityForMoveDown(request.getFromPriority(), request.getToPriority());
        taskRepository.updateTaskPriority(request.getId(), request.getToPriority());
    }

    public void toggleStatus(Integer id) {
        validateExistedItem(id);
        taskRepository.toggleCompleted(id);
    }

    public void validateExistedItem(Integer id) throws TaskNotFoundException {
        taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }
}
