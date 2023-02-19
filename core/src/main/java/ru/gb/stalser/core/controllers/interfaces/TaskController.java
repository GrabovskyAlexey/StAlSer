package ru.gb.stalser.core.controllers.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.stalser.api.dto.tag.TagDto;
import ru.gb.stalser.api.dto.task.TaskDto;
import ru.gb.stalser.api.dto.util.MessageDto;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Validated
@Tag(name = "task", description = "Контроллер для работы с задачами")
public interface TaskController {
    /**
     * GET /${stalser.api.url}/tasks : Get all tasks
     *
     * @return List of all tasks (status code 200)
     * or Bad Request (status code 400)
     */
    @Operation(
            operationId = "getAllTasks",
            summary = "Получение списка задач",
            tags = {"task"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список всех задач", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(
                                    schema = @Schema(implementation = TaskDto.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @GetMapping(
            produces = {"application/json"}
    )
    ResponseEntity<List<TaskDto>> getAllTasks(Principal principal);

    /**
     * GET /${stalser.api.url}/tasks/{id} : Get Task by id
     *
     * @param id Task id (required)
     * @return Get one task (status code 200)
     * or Bad Request (status code 400)
     * or Not found task (status code 404)
     */
    @Operation(
            operationId = "getTaskById",
            summary = "Получить задачу по идентификатору",
            tags = {"task"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Задача", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Задача не найдена", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @GetMapping(
            value = "/{id}",
            produces = {"application/json"}
    )
    ResponseEntity<TaskDto> getTaskById(
            @Parameter(name = "id", description = "task id", required = true)
            @PathVariable("id") Long id,
            Principal principal
    );

    /**
     * GET /${stalser.api.url}/tasks/tag : Get list of Task by Tag's name
     *
     * @param tagDto Tag item (required)
     * @return Get list of task (status code 200)
     * or Bad Request (status code 400)
     */
    @Operation(
            operationId = "getTasksByTagsName",
            summary = "Получить список задач по тегу",
            tags = {"task"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Задача", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
            }
    )
    @GetMapping(
            value = "/tag",
            produces = {"application/json"}
    )
    ResponseEntity<List<TaskDto>> getAllTaskByTag(
            @Parameter(name = "tag", description = "Tags item", required = true)
            @Valid @RequestBody TagDto tagDto
            );

    /**
     * POST /${stalser.api.url}/tasks : Add task
     *
     * @param task task Item (required)
     * @return Successfully add task (status code 201)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     */
    @Operation(
            operationId = "addTask",
            summary = "Добавление задачи",
            tags = {"task"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешно добавленная задача", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            }

    )
    @PostMapping(
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<TaskDto> addTask(
            @Parameter(name = "Task", description = "Task Item", required = true)
            @Valid @RequestBody TaskDto task,
            Principal principal
    );

    /**
     * PUT /${stalser.api.url}/tasks/{id} : Update task by id
     *
     * @param task task item (required)
     * @return Successfully update task (status code 204)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     * or Not found task (status code 404)
     */
    @Operation(
            operationId = "updateTask",
            summary = "Обновить задачу",
            tags = {"task"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Задача успешно обновлена"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Задача не найдена", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @PutMapping(
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateTask(
            @Parameter(name = "id", description = "task id", required = true) @PathVariable("id") Long id,
            @Parameter(name = "Task", description = "Task Item", required = true) @Valid @RequestBody TaskDto task,
            Principal principal
    );

    /**
     * DELETE /${stalser.api.url}/tasks/{id} : Delete task by id
     *
     * @param id task id (required)
     * @return Successfully delete task (status code 204)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     * or Not found task (status code 404)
     */
    @Operation(
            operationId = "deleteTask",
            summary = "Удаление задачи",
            tags = {"task"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Задача успешно удалена"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Задача не найдена", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @DeleteMapping(
            value = "/{id}",
            produces = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTask(
            @Parameter(name = "id", description = "task id", required = true)
            @PathVariable("id") Long id,
            Principal principal
    );

}
