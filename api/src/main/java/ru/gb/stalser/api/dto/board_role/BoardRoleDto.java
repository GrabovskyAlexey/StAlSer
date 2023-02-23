package ru.gb.stalser.api.dto.board_role;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.stalser.api.dto.restriction.RestrictionDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardRoleDto {
    private Long id;

    @JsonProperty("roleName")
    private String roleName;

    @JsonProperty("roleDesc")
    private String roleDesc;

    @JsonProperty("restrictions")
    private List<RestrictionDto> restrictions;
}
