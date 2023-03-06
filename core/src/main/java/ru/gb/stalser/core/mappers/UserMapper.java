package ru.gb.stalser.core.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gb.stalser.api.dto.user.UserDto;
import ru.gb.stalser.core.entity.Board;
import ru.gb.stalser.core.entity.User;
import ru.gb.stalser.core.services.interfaces.BoardService;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, imports = {Board.class, Collectors.class})
public abstract class UserMapper {
    @Autowired
    protected BoardService boardService;

    @Mappings({
            @Mapping(target = "displayName", source = "profile.displayName"),
            @Mapping(target = "telegram", source = "profile.telegram"),
            @Mapping(target = "boardId", expression = "java(boardService.findAllBoardWithUser(user).stream().map(Board::getId).collect(Collectors.toList()))")})
    public abstract UserDto mapToDto(User user);

    public abstract User mapFromDto(UserDto userDto);
}
