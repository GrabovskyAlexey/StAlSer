package ru.gb.stalser.core.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.stalser.api.dto.sprint.SprintDto;
import ru.gb.stalser.core.entity.Sprint;

@Mapper(uses = {BoardMapper.class})
public interface SprintMapper {
    @Mapping(source = "boardDto", target = "board")
    Sprint mapFromDto(SprintDto sprintDto);

    @InheritInverseConfiguration
    SprintDto mapToDto(Sprint sprint);



}
