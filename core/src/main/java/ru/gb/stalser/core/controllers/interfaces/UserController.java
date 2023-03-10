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
import ru.gb.stalser.api.dto.auth.*;
import ru.gb.stalser.api.dto.util.MessageDto;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;
import java.security.Principal;


@Tag(name = "auth", description = "Контроллер для аутентификации")
public interface UserController {

    /**
     * POST /auth : create token
     *
     * @param authRequest authRequest Item (required)
     * @return Successfully create token (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     */
    @Operation(
            operationId = "authenticate",
            summary = "Аутентификация",
            tags = {"auth"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешная аутентификация", content = {
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
            consumes = {"application/json"},
            path = {"/auth"}
    )
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<AuthResponse> createAuthToken(
            @Parameter(name = "AuthRequest", description = "AuthRequest Item", required = true) @Valid @RequestBody AuthRequest authRequest
    );

    /**
     * POST /register : register user
     *
     * @param registerRequest registerRequest Item (required)
     * @return Successfully create token (status code 200)
     * or Bad Request (status code 400)
     */
    @Operation(
            operationId = "register",
            summary = "Регистрация",
            tags = {"auth"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Токен авторизации при успешной регистрации", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
            }
    )
    @PostMapping(
            produces = {"application/json"},
            consumes = {"application/json"},
            path = {"/register"}
    )
    @ResponseStatus(HttpStatus.CREATED)
    AuthResponse register(
            @Parameter(name = "RegisterRequest", description = "RegisterRequest Item", required = true)
            @Valid @RequestBody RegisterRequest registerRequest
    );



    @Operation(
            operationId = "userUpdate",
            summary = "Смена пароля",
            tags = {"auth"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Токен авторизации при успешной смене пароля", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
            }
    )
    @PostMapping(
            produces = {"application/json"},
            consumes = {"application/json"},
            path = {"/password/change"}
    )

    ResponseEntity<AuthResponse> changePassword(
            @Parameter(name = "RegisterUpdateRequest", description = "AuthRequestPassUpdate Item", required = true)
            @Valid @RequestBody AuthRequestPassUpdate authRequestPassUpdate,
            Principal principal
            );

    /**
     * POST /register : register user
     *
     * @param refreshRequest registerRequest Item (required)
     * @return Successfully create token (status code 200)
     * or Bad Request (status code 400)
     */
    @Operation(
            operationId = "refresh",
            summary = "Получение нового токена",
            tags = {"auth"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Новая пара токенов доступа и обновления", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }),
            }
    )
    @PostMapping(
            produces = {"application/json"},
            consumes = {"application/json"},
            path = {"/refresh"}
    )
    ResponseEntity<AuthResponse> refresh(
            @Parameter(name = "RefreshRequest", description = "RefreshRequest Item", required = true)
            @Valid @RequestBody RefreshRequest refreshRequest
    ) throws AuthException;

}
