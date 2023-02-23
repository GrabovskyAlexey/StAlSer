package ru.gb.stalser.core.mappers;

import org.mapstruct.Mapper;
import ru.gb.stalser.api.dto.board_role.BoardRoleDto;
import ru.gb.stalser.core.entity.BoardRole;

@Mapper
public interface RoleBoardMapper {

    BoardRoleDto mapToDto(BoardRole boardRole);

    BoardRole mapFromDto (BoardRoleDto boardRoleDto);
}
