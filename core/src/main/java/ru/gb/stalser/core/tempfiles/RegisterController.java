package ru.grabovsky.springmarket.controllers.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.grabovsky.market.api.dto.auth.AuthResponseDto;
import ru.grabovsky.market.api.dto.util.MessageDto;
import ru.grabovsky.market.api.dto.register.RegisterRequestDto;

import javax.validation.Valid;

/**
 * Интерфейс RegisterController с аннотациями для генерации OpenApi документации
 *
 * @author grabovsky.alexey
 */

@Validated
@Tag(name = "register", description = "Registration api")
public interface RegisterController {

    /**
     * POST /register : Authenticate
     *
     * @param request Registration Request (required)
     * @return Send token after successfully registration (status code 200)
     *         or Bad Request (status code 400)
     *         or Unauthorized (status code 400)
     */
    @Operation(
            operationId = "register",
            summary = "Registration",
            tags = { "register" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Send token after successfully registration", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    })
            }
    )
    @PostMapping(
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    ResponseEntity<AuthResponseDto> register(
            @Parameter(name = "RegisterRequestDto", description = "Registration request", required = true) @Valid @RequestBody RegisterRequestDto request
            );
}
