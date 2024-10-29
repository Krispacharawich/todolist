package com.backend.TodoList.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "task")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "priority")
    private Integer priority;
    @Column(name = "completed")
    private Boolean completed;
    @CreatedDate
    @Column(name = "createdAt")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updatedAt")
    private Date updatedAt;

}
