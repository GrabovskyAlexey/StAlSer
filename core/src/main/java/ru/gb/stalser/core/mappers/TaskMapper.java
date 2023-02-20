package ru.gb.stalser.core.mappers;

import org.mapstruct.Mapper;
import ru.gb.stalser.api.dto.task.TaskDto;
import ru.gb.stalser.core.entity.Task;

@Mapper(uses = {UserMapper.class})
public interface TaskMapper {
    TaskDto mapToDto(Task entity);
    Task mapFromDto(TaskDto dto);
}
