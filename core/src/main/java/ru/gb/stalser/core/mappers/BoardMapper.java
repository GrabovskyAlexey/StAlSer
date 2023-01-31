package ru.gb.stalser.core.mappers;

import org.mapstruct.Mapper;
import ru.gb.stalser.api.dto.board.BoardDto;
import ru.gb.stalser.core.entity.Board;

@Mapper
public interface BoardMapper {

    BoardDto mapToDto(Board entity);

    Board mapFromDto(BoardDto fto);
}