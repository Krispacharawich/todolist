package com.backend.todolist.controller;

import com.backend.todolist.exceptions.TaskNotFoundException;
import com.backend.todolist.model.Task;
import com.backend.todolist.model.request.AddTaskRequest;
import com.backend.todolist.model.request.UpdatePrioritizedTaskRequest;
import com.backend.todolist.model.response.TaskListResponse;
import com.backend.todolist.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@WebMvcTest(TaskController.class)
@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllTasks() throws Exception {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1, "Task 1", 1, false, Date.from(Instant.now()), Date.from(Instant.now())));
        taskList.add(new Task(2, "Task 2", 2, false, Date.from(Instant.now()), Date.from(Instant.now())));
        TaskListResponse taskListResponse = TaskListResponse.builder().taskList(taskList).build();

        when(taskService.getAllTasks()).thenReturn(taskListResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskList.length()", is(taskList.size()))) // Check that taskList contains two items
                .andExpect(jsonPath("$.taskList[0].id", is(1)))
                .andExpect(jsonPath("$.taskList[0].title", is("Task 1")))
                .andExpect(jsonPath("$.taskList[0].priority", is(1)))
                .andExpect(jsonPath("$.taskList[0].completed", is(false)))
                .andExpect(jsonPath("$.taskList[1].id", is(2)))
                .andExpect(jsonPath("$.taskList[1].title", is("Task 2")))
                .andExpect(jsonPath("$.taskList[1].priority", is(2)))
                .andExpect(jsonPath("$.taskList[1].completed", is(false)));
    }

    @Test
    void testAddTask() throws Exception {
        AddTaskRequest addTaskRequest = new AddTaskRequest();
        addTaskRequest.setTitle("Task 1");
        Task newTask = new Task(1, "New Task", 1, false, Date.from(Instant.now()), Date.from(Instant.now()));

        when(taskService.addTask(Mockito.any(AddTaskRequest.class))).thenReturn(newTask);

        mockMvc.perform(post("/api/tasks/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addTaskRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(newTask.getId())))
                .andExpect(jsonPath("$.title", is(newTask.getTitle())))
                .andExpect(jsonPath("$.priority", is(newTask.getPriority())))
                .andExpect(jsonPath("$.completed", is(newTask.getCompleted())));
    }

    @Test
    void testToggleStatus() throws Exception {
        Integer taskId = 1;
        Mockito.doNothing().when(taskService).toggleStatus(taskId);

        mockMvc.perform(post("/api/tasks/{id}/toggle", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteTask() throws Exception {
        Integer taskId = 1;
        Mockito.doNothing().when(taskService).deleteTaskById(taskId);

        mockMvc.perform(delete("/api/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testPrioritizeMoveUpTasks() throws Exception {
        UpdatePrioritizedTaskRequest request = new UpdatePrioritizedTaskRequest();
        request.setId(3);
        request.setFromPriority(3);
        request.setToPriority(1);
        Mockito.doNothing().when(taskService).prioritizeMoveUpTasks(Mockito.any(UpdatePrioritizedTaskRequest.class));

        mockMvc.perform(post("/api/tasks/priority/move-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testPrioritizeMoveDownTasks() throws Exception {
        UpdatePrioritizedTaskRequest request = new UpdatePrioritizedTaskRequest();
        request.setId(1);
        request.setFromPriority(1);
        request.setToPriority(2);
        Mockito.doNothing().when(taskService).prioritizeMoveDownTasks(Mockito.any(UpdatePrioritizedTaskRequest.class));

        mockMvc.perform(post("/api/tasks/priority/move-down")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }


    @Test
    void testAddTaskWithMissingBody() throws Exception {
        mockMvc.perform(post("/api/tasks/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testToggleStatusWithNonExistingId() throws Exception {
        Integer nonExistingId = 999;
        doThrow(new TaskNotFoundException(nonExistingId)).when(taskService).toggleStatus(nonExistingId);

        mockMvc.perform(post("/api/tasks/{id}/toggle", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void testDeleteTaskWithNonExistingId() throws Exception {
        Integer nonExistingId = 999;
        doThrow(new TaskNotFoundException(nonExistingId)).when(taskService).deleteTaskById(nonExistingId);

        mockMvc.perform(delete("/api/tasks/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPrioritizeMoveUpTasksWithNoRequestBody() throws Exception {
        mockMvc.perform(post("/api/tasks/priority/move-up")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPrioritizeMoveDownTasksWithNoRequestBody() throws Exception {
        mockMvc.perform(post("/api/tasks/priority/move-down")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
