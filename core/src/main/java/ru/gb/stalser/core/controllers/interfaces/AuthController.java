package ru.gb.stalser.core.controllers.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.gb.stalser.api.dto.auth.AuthRequest;
import ru.gb.stalser.api.dto.auth.AuthResponse;
import ru.gb.stalser.api.dto.util.MessageDto;

import javax.validation.Valid;

@Tag(name = "auth", description = "Контроллер для аутентификации")
public interface AuthController {

    /**
     * POST /${stalser.api.url}/auth : create token
     *
     * @param authRequest authRequest Item (required)
     * @return Successfully create token (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     */
    @Operation(
            operationId = "authenticate",
            summary = "Authenticate",
            tags = {"auth"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully authenticate", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))
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
    ResponseEntity<?> createAuthToken(
            @Parameter(name = "AuthRequest", description = "AuthRequest Item", required = true) @Valid @RequestBody AuthRequest authRequest
    );

}
