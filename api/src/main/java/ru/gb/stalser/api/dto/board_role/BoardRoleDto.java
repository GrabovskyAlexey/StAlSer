package ru.gb.stalser.api.dto.board_role;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.stalser.api.dto.restriction.RestrictionDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardRoleDto {

    @Schema(description = "Идентификатор роли на доске", example = "1")
    @Min(value = 1)
    private Long id;

    @NotEmpty(message = "Название роли не может быть пустым")
    @Size(min = 1, max = 30, message = "Название роли должно содержать от 1 до 30 символов. Проверьте длину.")
    @Schema(description = "Название роли")
    @JsonProperty("roleName")
    private String roleName;

    @Size(max = 100, message = "Описание роли должно содержать до 100 символов. Проверьте длину.")
    @Schema(description = "Описание роли")
    @JsonProperty("roleDesc")
    private String roleDesc;

    @Schema(description = "Список ограничений для роли")
    @JsonProperty("restrictions")
    private List<RestrictionDto> restrictions;
}
