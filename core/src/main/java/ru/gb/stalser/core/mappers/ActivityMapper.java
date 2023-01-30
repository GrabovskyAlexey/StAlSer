package ru.gb.stalser.core.mappers;

import org.mapstruct.Mapper;
import ru.gb.stalser.api.dto.activity.ActivityDto;
import ru.gb.stalser.core.entity.Activity;

@Mapper(uses = {TaskMapper.class})
public interface ActivityMapper {
    ActivityDto mapToDto(Activity activity);
    Activity mapFromDto(ActivityDto activityDto);
}
