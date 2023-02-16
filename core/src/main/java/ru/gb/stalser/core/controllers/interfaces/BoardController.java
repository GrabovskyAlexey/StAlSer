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
import ru.gb.stalser.api.dto.board.BoardDto;
import ru.gb.stalser.api.dto.util.MessageDto;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Validated
@Tag(name = "board", description = "Контроллер для работы с досками")
public interface BoardController {

    /**
     * GET /${stalser.api.url}/boards : Get all boards
     *
     * @return List of all boards (status code 200)
     * or Bad Request (status code 400)
     */
    @Operation(
            operationId = "getAllBoards",
            summary = "Получение списка досок",
            tags = {"board"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список всех досок", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(
                                    schema = @Schema(implementation = BoardDto.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @GetMapping(
            produces = {"application/json"}
    )
    ResponseEntity<List<BoardDto>> getAllBoards(Principal principal);

    /**
     * GET /${stalser.api.url}/boards/{id} : Get board by id
     *
     * @param id Board id (required)
     * @return Get one board (status code 200)
     * or Bad Request (status code 400)
     * or Not found task (status code 404)
     */
    @Operation(
            operationId = "getBoardById",
            summary = "Получить доску по идентификатору",
            tags = {"board"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Доска", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BoardDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Доска не найдена", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @GetMapping(
            value = "/{id}",
            produces = {"application/json"}
    )
    ResponseEntity<BoardDto> getBoardById(
            @Parameter(name = "id", description = "board id", required = true)
            @PathVariable("id") Long id,
            Principal principal
    );

    /**
     * POST /${stalser.api.url}/board : Add board
     *
     * @param board board Item (required)
     * @return Successfully add board (status code 201)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     */
    @Operation(
            operationId = "addBoard",
            summary = "Создание доски",
            tags = {"board"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешно созданная доска", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BoardDto.class))
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
    ResponseEntity<BoardDto> addBoard(
            @Parameter(name = "Board", description = "Board Item", required = true)
            @Valid @RequestBody BoardDto board,
            Principal principal
    );

    /**
     * PUT /${stalser.api.url}/boards/{id} : Update board by id
     *
     * @param board board item (required)
     * @return Successfully update board (status code 204)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     * or Not found board (status code 404)
     */
    @Operation(
            operationId = "updateBoard",
            summary = "Обновить доску",
            tags = {"board"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Доска успешно обновлена"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Доска не найдена", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @PutMapping(
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateBoard(
            @Parameter(name = "id", description = "task id", required = true) @PathVariable("id") Long id,
            @Parameter(name = "Board", description = "Board Item", required = true)
            @Valid @RequestBody BoardDto board,
            Principal principal
    );
}
