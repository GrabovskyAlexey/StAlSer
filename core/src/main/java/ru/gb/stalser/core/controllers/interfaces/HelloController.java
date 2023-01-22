package ru.gb.stalser.core.controllers.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.stalser.api.dto.hello.HelloDto;
import ru.gb.stalser.api.dto.util.MessageDto;

import javax.validation.Valid;
import java.util.List;

@Validated
@Tag(name = "hello", description = "Контроллер для демонстрации возможностей OpenApi")
public interface HelloController {

    /**
     * GET /${stalser.api.url}/hello : Get all hello
     *
     * @return Test message (status code 200)
     * or Bad Request (status code 400)
     */
    @Operation(
            operationId = "getAllHello",
            summary = "Получение списка Hello",
            tags = {"hello"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Get all hello", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(
                                    schema = @Schema(implementation = HelloDto.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @GetMapping(
            produces = {"application/json"}
    )
    ResponseEntity<List<HelloDto>> getAllHello();

    /**
     * GET /${stalser.api.url}/hello/{id} : Get Hello by id
     *
     * @param id hello id (required)
     * @return Get one hello (status code 200)
     * or Bad Request (status code 400)
     * or Not found hello (status code 404)
     */
    @Operation(
            operationId = "getHelloById",
            summary = "Get hello by id",
            tags = {"hello"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Get one hello", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = HelloDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Hello not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @GetMapping(
            value = "/{id}",
            produces = {"application/json"}
    )
    ResponseEntity<HelloDto> getHelloById(
            @Parameter(name = "id", description = "hello id", required = true) @PathVariable("id") Long id
    );

    /**
     * POST /${stalser.api.url}/hello : Add hello
     *
     * @param hello hello Item (required)
     * @return Successfully add hello (status code 201)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     */
    @Operation(
            operationId = "addHello",
            summary = "Add hello",
            tags = {"hello"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully add hello", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
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
    ResponseEntity<MessageDto> addHello(
            @Parameter(name = "Hello", description = "Hello Item", required = true) @Valid @RequestBody HelloDto hello
    );

    /**
     * PUT /${stalser.api.url}/hello/{id} : Update hello by id
     *
     * @param hello hello item (required)
     * @return Successfully update hello (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     * or Not found hello (status code 404)
     */
    @Operation(
            operationId = "updateHello",
            summary = "Update hello",
            tags = {"hello"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully update hello", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Hello not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @PutMapping(
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    ResponseEntity<MessageDto> updateHello(
            @Parameter(name = "id", description = "hello id", required = true) @PathVariable("id") Long id,
            @Parameter(name = "Hello", description = "Hello Item", required = true) @Valid @RequestBody HelloDto hello
    );

    /**
     * DELETE /${stalser.api.url}/hello/{id} : Delete hello by id
     *
     * @param id       hello id (required)
     * @return Successfully delete hello (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     * or Not found hello (status code 404)
     */
    @Operation(
            operationId = "deleteHello",
            summary = "Delete hello",
            tags = {"hello"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully delete hello", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Hello not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @DeleteMapping(
            value = "/{id}",
            produces = {"application/json"}
    )
    @SecurityRequirement(name = "token")
    ResponseEntity<MessageDto> deleteHello(
            @Parameter(name = "id", description = "hello id", required = true) @PathVariable("id") Long id
    );
}
