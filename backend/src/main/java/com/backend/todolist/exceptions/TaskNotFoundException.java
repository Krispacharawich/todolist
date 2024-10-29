package com.backend.todolist.exceptions;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(Integer id) {
        super("Task id: " + id + " does not found");
    }
}
