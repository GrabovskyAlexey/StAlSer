package ru.gb.stalser.core.mappers;

import org.mapstruct.Mapper;
import ru.gb.stalser.api.dto.board_user_role.BoardUserRoleDto;
import ru.gb.stalser.core.entity.BoardUserRole;

@Mapper
public interface BoardUserRoleMapper {

    BoardUserRoleDto mapToDto(BoardUserRole boardUserRole);

    BoardUserRole mapFromDto(BoardUserRoleDto boardUserRoleDto);
}
