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
import ru.gb.stalser.api.dto.invite.InviteDto;
import ru.gb.stalser.api.dto.invite.InviteListResponse;
import ru.gb.stalser.api.dto.util.MessageDto;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Validated
@Tag(name = "invite", description = "Контроллер для работы с приглашениями")
public interface InviteController {

    /**
     * GET /${stalser.api.url}/invites : Get all invites
     *
     * @return List of all invites (status code 200)
     * or Bad Request (status code 400)
     */
    @Operation(
            operationId = "getAllInvites",
            summary = "Получение списка приглашений",
            tags = {"invite"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список всех приглашений", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = InviteListResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @GetMapping(
            produces = {"application/json"}
    )
    InviteListResponse getAllInvites(Principal principal);

    /**
     * GET /${stalser.api.url}/invites/{id} : Get invite by id
     *
     * @param id invite id (required)
     * @return Get one invite (status code 200)
     * or Bad Request (status code 400)
     * or Not found invite (status code 404)
     */
    @Operation(
            operationId = "getInviteById",
            summary = "Получить приглашение по идентификатору",
            tags = {"invite"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Приглашение", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = InviteDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Приглашение не найдена", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @GetMapping(
            value = "/{id}",
            produces = {"application/json"}
    )
    InviteDto getInviteById(
            @Parameter(name = "id", description = "идентификатор приглашения", required = true)
            @PathVariable("id") Long id,
            Principal principal
    );

    /**
     * POST /${stalser.api.url}/invites : Add invite
     *
     * @param invite invite Item (required)
     * @return Successfully add invite (status code 201)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     */
    @Operation(
            operationId = "addInvite",
            summary = "Добавление приглашения",
            tags = {"invite"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Приглашение успешно добавлено", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = InviteDto.class))
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
    InviteDto addInvite(
            @Parameter(name = "invite", description = "Приглашение", required = true)
            @Valid @RequestBody InviteDto invite,
            Principal principal
    );

    /**
     * PUT /${stalser.api.url}/invites/{id} : Update invite by id
     *
     * @param invite invite item (required)
     * @return Successfully update invite (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     * or Not found invite (status code 404)
     */
    @Operation(
            operationId = "updateInvite",
            summary = "Обновление приглашения",
            tags = {"invite"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Приглашение успешно обновлено"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Приглашение не найдено", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @PutMapping(
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateInvite(
            @Parameter(name = "id", description = "идентификатор приглашения", required = true) @PathVariable("id") Long id,
            @Parameter(name = "invite", description = "Приглашение", required = true)
            @Valid @RequestBody InviteDto invite,
            Principal principal
    );

    /**
     * DELETE /${stalser.api.url}/invites/{id} : Delete invite by id
     *
     * @param id invite id (required)
     * @return Successfully delete invite (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     * or Not found invite (status code 404)
     */
    @Operation(
            operationId = "deleteInvite",
            summary = "Удаление приглашения",
            tags = {"invite"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Приглашение успешно удалено"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Приглашение не найдено", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @DeleteMapping(
            value = "/{id}",
            produces = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteInvite(
            @Parameter(name = "id", description = "идентификатор приглашения", required = true)
            @PathVariable("id") Long id,
            Principal principal
    );
}
