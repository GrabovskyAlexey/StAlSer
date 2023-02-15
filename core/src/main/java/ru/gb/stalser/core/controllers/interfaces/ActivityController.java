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
import ru.gb.stalser.api.dto.activity.ActivityDto;
import ru.gb.stalser.api.dto.util.MessageDto;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Validated
@Tag(name = "activity", description = "Контроллер для работы с активностями")
public interface ActivityController {

    /**
     * GET /${stalser.api.url}/activity : Get all activities
     *
     * @return List of all activities (status code 200)
     * or Bad Request (status code 400)
     */
    @Operation(
            operationId = "getAllActivities",
            summary = "Получение списка активностей",
            tags = {"activity"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список всех активностей", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(
                                    schema = @Schema(implementation = ActivityDto.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @GetMapping(
            produces = {"application/json"}
    )
    ResponseEntity<List<ActivityDto>> getAllActivities(Principal principal);

    /**
     * GET /${stalser.api.url}/activity/{id} : Get activity by id
     *
     * @param id activity id (required)
     * @return Get one activity (status code 200)
     * or Bad Request (status code 400)
     * or Not found activity (status code 404)
     */
    @Operation(
            operationId = "getActivityById",
            summary = "Получить активность по идентификатору",
            tags = {"activity"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Активность", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Активность не найдена", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @GetMapping(
            value = "/{id}",
            produces = {"application/json"}
    )
    ResponseEntity<ActivityDto> getActivityById(
            @Parameter(name = "id", description = "activity id", required = true)
            @PathVariable("id") Long id,
            Principal principal
    );

    /**
     * POST /${stalser.api.url}/activity : Add activity
     *
     * @param activity activity Item (required)
     * @return Successfully add activity (status code 201)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     */
    @Operation(
            operationId = "addActivity",
            summary = "Добавление активности",
            tags = {"activity"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Активность успешно добавлена", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityDto.class))
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
    ResponseEntity<ActivityDto> addActivity(
            @Parameter(name = "activity", description = "Activity Item", required = true)
            @Valid @RequestBody ActivityDto activity,
            Principal principal
    );

    /**
     * PUT /${stalser.api.url}/activity/{id} : Update activity by id
     *
     * @param activity activity item (required)
     * @return Successfully update activity (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     * or Not found invite (status code 404)
     */
    @Operation(
            operationId = "updateActivity",
            summary = "Обновление активности",
            tags = {"activity"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Активность успешно обновлена", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Активность не найдена", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @PutMapping(
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateActivity(
            @Parameter(name = "id", description = "activity id", required = true) @PathVariable("id") Long id,
            @Parameter(name = "Activity", description = "Activity Item", required = true)
            @Valid @RequestBody ActivityDto activity,
            Principal principal
    );

    /**
     * DELETE /${stalser.api.url}/activity/{id} : Delete activity by id
     *
     * @param id       activity id (required)
     * @return Successfully delete activity (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     * or Not found activity (status code 404)
     */
    @Operation(
            operationId = "deleteActivity",
            summary = "Удаление активности",
            tags = {"activity"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Активность успешно удалена"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Активность не найдена", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @DeleteMapping(
            value = "/{id}",
            produces = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteActivity(
            @Parameter(name = "id", description = "activity id", required = true)
            @PathVariable("id") Long id,
            Principal principal
    );
}
