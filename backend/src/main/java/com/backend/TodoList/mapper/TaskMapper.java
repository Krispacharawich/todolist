package com.backend.TodoList.mapper;

import com.backend.TodoList.model.Task;
import com.backend.TodoList.repository.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    Task taskEntityToTask(TaskEntity taskEntity);
}
