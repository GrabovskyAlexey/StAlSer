package ru.gb.stalser.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.gb.stalser.api.dto.user.UserDto;
import ru.gb.stalser.core.entity.User;
@Mapper
public interface UserMapper {
    @Mappings({
            @Mapping(target = "displayName", source = "profile.displayName"),
            @Mapping(target = "telegram", source = "profile.telegram")})
    UserDto mapToDto(User user);
    User mapFromDto(UserDto userDto);
}
