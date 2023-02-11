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
import ru.gb.stalser.api.dto.tag.TagDto;
import ru.gb.stalser.api.dto.util.MessageDto;

import javax.validation.Valid;
import java.util.List;

@Validated
@Tag(name = "tag", description = "Контроллер для работы с тегом")
public interface TagController {

    /**
     * GET /${stalser.api.url}/tag : Get all tags
     *
     * @return List of all tags (status code 200)
     * or Bad Request (status code 400)
     */
    @Operation(
            operationId = "getAllTags",
            summary = "Получение списка тегов",
            tags = {"tag"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список всех тегов", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(
                                    schema = @Schema(implementation = TagDto.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @GetMapping(
            produces = {"application/json"}
    )
    ResponseEntity<List<TagDto>> getAllTags();


    /**
     * GET /${stalser.api.url}/tag/{id} : Get tag by id
     *
     * @param id tag id (required)
     * @return Get one tag (status code 200)
     * or Bad Request (status code 400)
     * or Not found tag (status code 404)
     */
    @Operation(
            operationId = "getTagById",
            summary = "Получить тег по идентификатору",
            tags = {"tag"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Тег", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TagDto.class))
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
    ResponseEntity<TagDto> getTagById(
            @Parameter(name = "id", description = "tag id", required = true) @PathVariable("id") Long id
    );

    /**
     * POST /${stalser.api.url}/tag : Add tag
     *
     * @param tag tag Item (required)
     * @return Successfully add tag (status code 201)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     */
    @Operation(
            operationId = "addTag",
            summary = "Добавление тега",
            tags = {"tag"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Тег успешно добавлен", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TagDto.class))
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
    ResponseEntity<TagDto> addTag(
            @Parameter(name = "tag", description = "Tag Item", required = true) @Valid @RequestBody TagDto tag
    );

    /**
     * PUT /${stalser.api.url}/tag/{id} : Update tag by id
     *
     * @param id tag id (required)
     * @return Successfully update tag (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     * or Not found (status code 404)
     */
    @Operation(
            operationId = "updateTag",
            summary = "Обновление тега",
            tags = {"tag"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Тег успешно обновлен", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TagDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Тег не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @PutMapping(
            produces = {"application/json"},
            consumes = {"application/json"},
            value = "/{id}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateTag(
            @Parameter(name = "id", description = "Tag id", required = true) @PathVariable("id") Long id
            );

    /**
     * DELETE /${stalser.api.url}/tag/{id} : Delete tag by id
     *
     * @param id tag id (required)
     * @return Successfully delete tag (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     * or Not found (status code 404)
     */
    @Operation(
            operationId = "deleteTag",
            summary = "Удаление тега",
            tags = {"tag"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Тег успешно удален"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Тег не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @DeleteMapping(
            value = "/{id}",
            produces = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTag(
            @Parameter(name = "id", description = "Tag id", required = true) @PathVariable("id") Long id
    );

}
