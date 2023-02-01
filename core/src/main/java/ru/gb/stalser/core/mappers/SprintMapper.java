package ru.gb.stalser.core.mappers;

import org.mapstruct.Mapper;
import ru.gb.stalser.api.dto.sprint.SprintDto;
import ru.gb.stalser.core.entity.Sprint;

@Mapper
public interface SprintMapper {

    SprintDto mapToDto(Sprint sprint);

    Sprint mapFromDto(SprintDto sprintDto);

}
