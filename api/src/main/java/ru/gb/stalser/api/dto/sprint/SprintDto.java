package ru.gb.stalser.api.dto.sprint;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.stalser.api.dto.board.BoardDto;
import ru.gb.stalser.api.dto.task.TaskDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация о спринте", name = "Sprint")
public class SprintDto {

    @Schema(description = "Идентификатор спринта", example = "1")
    @Min(value = 1)
    private Long id;

    @NotEmpty(message = "Спринт не может быть вне доски")
    @Schema(description = "Идентификатор доски")
    private BoardDto board;

    @NotEmpty(message = "Номер спринта не может быть пустым")
    @Schema(description = "Номер спринта", example = "1")
    @Min(value = 1)
    private Integer sprintNumber;

    @NotEmpty(message = "Дата начала спринта не может быть пустой")
    @Schema(description = "Дата начала спринта", implementation = Instant.class)
    @JsonProperty("startDate")
    private Date startDate;

    @NotEmpty(message = "Дата окончания спринта не может быть пустой")
    @Schema(description = "Дата окончания спринта", implementation = Instant.class)
    @JsonProperty("endDate")
    private Date endDate;

    @Schema(description = "Список задач")
    @JsonProperty("tasks")
    private List<TaskDto> tasks;

}
