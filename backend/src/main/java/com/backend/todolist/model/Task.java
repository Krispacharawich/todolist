package com.backend.todolist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Task {
    private Integer id;
    private String title;
    private Integer priority;
    private Boolean completed;
    private Date createdAt;
    private Date updatedAt;
}
