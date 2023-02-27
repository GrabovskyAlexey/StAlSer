package ru.gb.stalser.api.dto.board_user_role;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.stalser.api.dto.board_role.BoardRoleDto;
import ru.gb.stalser.api.dto.user.UserDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardUserRoleDto {
    @JsonProperty("user")
    private UserDto user;
    @JsonProperty("boardRole")
    private BoardRoleDto boardRole;
}
