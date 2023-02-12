package ru.gb.stalser.core.controllers.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.gb.stalser.api.dto.auth.AuthRequest;
import ru.gb.stalser.api.dto.auth.AuthResponse;
import ru.gb.stalser.api.dto.auth.RegisterRequest;
import ru.gb.stalser.api.dto.user.UserDto;
import ru.gb.stalser.api.dto.util.MessageDto;

import javax.validation.Valid;

@Tag(name = "reg", description = "Контроллер для регистрации")
public interface RegController {

    /**
     * POST /reg : create token
     *
     * @param registerRequest registerRequest Item (required)
     * @return Successfully create token (status code 200)
     * or Bad Request (status code 400)
     * or Forbidden (status code 403)
     */
    @Operation(
            operationId = "registration",
            summary = "Регистрация",
            tags = {"reg"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешная регистрация", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            }
    )
    @PostMapping(
            produces = {"application/json"},
            consumes = {"application/json"},
            path = {"/registration"}
    )
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<AuthResponse> regUser(
            @Parameter(name = "registerRequest", description = "registerRequest Item", required = true) @Valid @RequestBody RegisterRequest registerRequest
            );




}
