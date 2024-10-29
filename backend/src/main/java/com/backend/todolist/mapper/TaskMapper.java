package com.backend.todolist.mapper;

import com.backend.todolist.model.Task;
import com.backend.todolist.repository.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    Task taskEntityToTask(TaskEntity taskEntity);
}
