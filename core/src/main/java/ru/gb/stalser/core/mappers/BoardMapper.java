package ru.gb.stalser.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.stalser.api.dto.board.BoardDto;
import ru.gb.stalser.api.dto.board.BoardDtoForUser;
import ru.gb.stalser.core.entity.Board;
import ru.gb.stalser.core.entity.User;

@Mapper(uses = {UserMapper.class, RestrictionMapper.class})
public interface BoardMapper {

    BoardDto mapToDto(Board entity);
    @Mapping(target = "restrictions", expression = "java(restrictionListToRestrictionDtoList(entity.getUsersWithRoles().get(user).getRestrictions()))")
    @Mapping(target = "id", source = "entity.id")
    BoardDtoForUser mapToDtoForUser(Board entity, User user);

    Board mapFromDto(BoardDto fto);
}
