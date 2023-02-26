package ru.gb.stalser.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.stalser.api.dto.board.BoardDto;
import ru.gb.stalser.core.entity.Board;

@Mapper(uses = {UserMapper.class})
public interface BoardMapper {

    BoardDto mapToDto(Board entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Board mapFromDto(BoardDto fto);
}
