package com.backend.TodoList.service;

import com.backend.TodoList.exceptions.TaskNotFoundException;
import com.backend.TodoList.mapper.TaskMapper;
import com.backend.TodoList.model.Task;
import com.backend.TodoList.model.request.AddTaskRequest;
import com.backend.TodoList.model.request.UpdatePrioritizedTaskRequest;
import com.backend.TodoList.model.response.TaskListResponse;
import com.backend.TodoList.repository.TaskRepository;
import com.backend.TodoList.repository.entity.TaskEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;
    AddTaskRequest request = new AddTaskRequest();
    TaskEntity taskEntity1 = new TaskEntity();
    TaskEntity taskEntity2 = new TaskEntity();
    TaskEntity taskEntity3 = new TaskEntity();
    Task task1 = new Task(1, "Task1", 1, false, Date.from(Instant.now()), Date.from(Instant.now()));
    Task task2 = new Task(2, "Task2", 2, false, Date.from(Instant.now()), Date.from(Instant.now()));
    Task task3 = new Task(3, "Task3", 3, false, Date.from(Instant.now()), Date.from(Instant.now()));

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request.setTitle("Task1");
        taskEntity1.setId(1);
        taskEntity1.setTitle("Task1");
        taskEntity1.setPriority(1);
        taskEntity1.setCompleted(false);
        taskEntity2.setId(2);
        taskEntity2.setTitle("Task2");
        taskEntity2.setPriority(2);
        taskEntity2.setCompleted(false);
        taskEntity3.setId(3);
        taskEntity3.setTitle("Task3");
        taskEntity3.setPriority(3);
        taskEntity3.setCompleted(false);
    }

    @Test
    void testAddTask() {
        when(taskRepository.findFirstByOrderByIdDesc()).thenReturn(taskEntity1);
        when(taskMapper.taskEntityToTask(taskEntity1)).thenReturn(task1);

        Task result = taskService.addTask(request);

        verify(taskRepository, times(1)).insertTask(request.getTitle());
        verify(taskRepository, times(1)).findFirstByOrderByIdDesc();
        verify(taskMapper, times(1)).taskEntityToTask(taskEntity1);

        assertThat(result).isEqualTo(task1);
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAllByOrderByPriorityAsc()).thenReturn(List.of(taskEntity1, taskEntity2, taskEntity3));
        when(taskMapper.taskEntityToTask(taskEntity1)).thenReturn(task1);
        when(taskMapper.taskEntityToTask(taskEntity2)).thenReturn(task2);
        when(taskMapper.taskEntityToTask(taskEntity3)).thenReturn(task3);

        TaskListResponse response = taskService.getAllTasks();

        verify(taskRepository, times(1)).findAllByOrderByPriorityAsc();
        verify(taskMapper, times(1)).taskEntityToTask(taskEntity1);
        verify(taskMapper, times(1)).taskEntityToTask(taskEntity2);
        verify(taskMapper, times(1)).taskEntityToTask(taskEntity3);

        assertThat(response.getTaskList()).containsExactly(task1, task2, task3);
    }

    @Test
    void testDeleteTaskById() {
        Integer taskId = 1;

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity1));

        taskService.deleteTaskById(taskId);

        verify(taskRepository, times(1)).updateOtherTasksPriorityForRemove(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    void testDeleteTaskById_NotFound() {
        Integer taskId = 1;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTaskById(taskId));
        verify(taskRepository, never()).updateOtherTasksPriorityForRemove(taskId);
        verify(taskRepository, never()).deleteById(taskId);
    }

    @Test
    void testPrioritizeMoveUpTasks() {
        UpdatePrioritizedTaskRequest request = new UpdatePrioritizedTaskRequest();
        request.setId(3);
        request.setFromPriority(3);
        request.setToPriority(1);

        when(taskRepository.findById(request.getId())).thenReturn(Optional.of(taskEntity3));

        taskService.prioritizeMoveUpTasks(request);

        verify(taskRepository, times(1)).updateOtherTasksPriorityForMoveUp(request.getFromPriority(), request.getToPriority());
        verify(taskRepository, times(1)).updateTaskPriority(request.getId(), request.getToPriority());
    }

    @Test
    void testPrioritizeMoveDownTasks() {
        UpdatePrioritizedTaskRequest request = new UpdatePrioritizedTaskRequest();
        request.setId(1);
        request.setFromPriority(1);
        request.setToPriority(3);

        when(taskRepository.findById(request.getId())).thenReturn(Optional.of(taskEntity1));

        taskService.prioritizeMoveDownTasks(request);

        verify(taskRepository, times(1)).updateOtherTasksPriorityForMoveDown(request.getFromPriority(), request.getToPriority());
        verify(taskRepository, times(1)).updateTaskPriority(request.getId(), request.getToPriority());
    }

    @Test
    void testToggleStatus() {
        Integer taskId = 1;
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity1));

        taskService.toggleStatus(taskId);

        verify(taskRepository, times(1)).toggleCompleted(taskId);
    }

    @Test
    void testToggleStatus_NotFound() {
        Integer taskId = 1;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.toggleStatus(taskId));
        verify(taskRepository, never()).toggleCompleted(taskId);
    }
}
