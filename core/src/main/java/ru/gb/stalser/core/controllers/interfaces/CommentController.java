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
import ru.gb.stalser.api.dto.comment.CommentDto;
import ru.gb.stalser.api.dto.util.MessageDto;

import java.security.Principal;
import java.util.List;


import javax.validation.Valid;

@Validated
@Tag(name = "comment", description = "Контроллер для работы с комментариями")
public interface CommentController {
    /**
     * GET /${stalser.api.url}/comments : Get all comments
     *
     * @return List of all comments (status code 200)
     * or Bad Request (status code 400)
     */
    @Operation(
            operationId = "getAllComments",
            summary = "Получение списка коментариев",
            tags = {"comment"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список всех коментариев", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(
                                    schema = @Schema(implementation = CommentDto.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @GetMapping(
            produces = {"application/json"}
    )
    ResponseEntity<List<CommentDto>> getAllComments(Principal principal);

    /**
     * GET /${stalser.api.url}/comments/{id} : Get Comment by id
     *
     * @param id Comment id (required)
     * @return Get one comment (status code 200)
     * or Bad Request (status code 400)
     * or Not found comment (status code 404)
     */
    @Operation(
            operationId = "getCommentById",
            summary = "Получить комментарий по id",
            tags = {"comment"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Комментарий: ", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "комментарий не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @GetMapping(
            value = "/{id}",
            produces = {"application/json"}
    )
    ResponseEntity<CommentDto> getCommentById(
            @Parameter(name = "id", description = "task id", required = true)
            @PathVariable("id") Long id,
            Principal principal
    );

    /**
     * POST /${stalser.api.url}/comments  : Add comment
     *
     * @param comment comment Item (required)
     * @return Successfully add comment (status code 201)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     */
    @Operation(
            operationId = "addComment",
            summary = "Добавление Комментария",
            tags = {"comment"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Комментарий успешно добавлен", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))
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
    ResponseEntity<CommentDto> addComment(
            @Parameter(name = "Comment", description = "Comment Item", required = true)
            @Valid @RequestBody CommentDto comment,
            Principal principal
    );

    /**
     * PUT /${stalser.api.url}/comments/{id} : Update comment by id
     *
     * @param comment comment item (required)
     */
    @Operation(
            operationId = "updateComment",
            summary = "Обновить комментарий",
            tags = {"comment"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Комментарий успешно обновлен"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Комментарий  не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @PutMapping(
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateComment(
            @Parameter(name = "id", description = "comment id", required = true) @PathVariable("id") Long id,
            @Parameter(name = "Comment", description = "Comment Item", required = true)
            @Valid @RequestBody CommentDto comment,
            Principal principal
    );

}




