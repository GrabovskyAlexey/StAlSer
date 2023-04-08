package ru.gb.stalser.api.dto.sprint;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Список спринтов", name = "SprintList")
public class SprintListResponse {
    @Schema(description = "Спринты")
    private List<SprintDto> sprints;
}
