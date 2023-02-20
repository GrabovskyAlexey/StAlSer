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
import ru.gb.stalser.api.dto.sprint.SprintDto;
import ru.gb.stalser.api.dto.util.MessageDto;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Validated
@Tag(name = "sprint", description = "Контроллер для спринта")
public interface SprintController {

    @Operation(
            operationId = "getAllSprints",
            summary = "Получение списка спринтов",
            tags = {"sprint"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список всех спринтов", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(
                                    schema = @Schema(implementation = SprintDto.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )

    @GetMapping(produces = {"application/json"})
    ResponseEntity<List<SprintDto>> getAllSprints(Principal principal);

    @Operation(
            operationId = "getSprintById",
            summary = "Получить спринт по идентификатору",
            tags = {"sprint"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Спринт", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = SprintDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Спринт не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )

    @GetMapping(value = "/{id}", produces = {"application/json"})
    ResponseEntity<SprintDto> getSprintById(
            @Parameter(name = "id", description = "sprint id", required = true)
            @PathVariable("id") Long id,
            Principal principal
    );

    @Operation(
            operationId = "addSprint",
            summary = "Создание спринта",
            tags = {"sprint"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Спринт создан", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = SprintDto.class))
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
    ResponseEntity<SprintDto> addSprint(
            @Parameter(name = "Sprint", description = "Sprint Item", required = true)
            @Valid
            @RequestBody SprintDto sprint,
            Principal principal
    );

    @Operation(
            operationId = "updateSprint",
            summary = "Обновить спринт",
            tags = {"sprint"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Спринт успешно обновлен"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Спринт не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @PutMapping(
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateSprint(
            @Parameter(name = "id", description = "Sprint id", required = true)
            @PathVariable("id") Long id,
            @Parameter(name = "Sprint", description = "Sprint Item", required = true)
            @Valid @RequestBody SprintDto sprint,
            Principal principal
    );

    @Operation(
            operationId = "deleteSprint",
            summary = "Удаление спринта",
            tags = {"sprint"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Спринт успешно удален"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Спринт не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @DeleteMapping(
            value = "/{id}",
            produces = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSprint(
            @Parameter(name = "id", description = "sprint id", required = true)
            @PathVariable("id") Long id,
            Principal principal
    );

}
