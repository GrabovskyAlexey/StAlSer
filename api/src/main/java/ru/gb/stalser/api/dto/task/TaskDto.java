package ru.gb.stalser.api.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.stalser.api.dto.board.BoardDto;
import ru.gb.stalser.api.dto.sprint.SprintDto;
import ru.gb.stalser.api.dto.tag.TagDto;
import ru.gb.stalser.api.dto.user.UserDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация о задаче", name = "Task")
public class TaskDto {

    @Schema(description = "Идентификатор задачи", example = "1")
    @Min(value = 1)
    private Long id;

    @NotEmpty(message = "Название задачи не может быть пустым")
    @Size(min = 3, message = "Название задачи должно быть минимум 3 символа")
    @Schema(description = "Название задачи")
    @JsonProperty("taskName")
    private String name;

    @NotEmpty(message = "Описание задачи не может быть пустым")
    @Size(min = 3, message = "Описание задачи должно быть минимум 3 символа")
    @Schema(description = "Описание задачи")
    @JsonProperty("taskDescription")
    private String description;

    @Schema(description = "Идентификатор доски", example = "1")
    @Min(value = 1)
    private BoardDto board;

    @NotEmpty(message = "Статус задачи не может быть пустым")
    @Schema(description = "Статус задачи", implementation = TaskStatus.class)
    @JsonProperty("taskStatus")
    private TaskStatus status;

    @NotEmpty(message = "Приоритет задачи не может быть пустым")
    @Schema(description = "Приоритет задачи", implementation = TaskPriority.class)
    @JsonProperty("taskPriority")
    private TaskPriority priority;

    @NotEmpty(message = "Тип задачи не может быть пустым")
    @Schema(description = "Тип задачи", implementation = TaskType.class)
    @JsonProperty("taskType")
    private String type;

    @Schema(description = "Дедлайн", implementation = Instant.class)
    @JsonProperty("deadline")
    private Date deadline;

    @Schema(description = "Исполнитель")
    @JsonProperty("assignee")
    private UserDto assignee;

    @Schema(description = "Создатель")
    @JsonProperty("creator")
    private UserDto creator;

    @Schema(description = "Список спринтов в которых")
    @JsonProperty("tags")
    private List<TagDto> tags;

    @Schema(description = "Список спринтов")
    @JsonProperty("sprints")
    private List<SprintDto> sprints;
}
