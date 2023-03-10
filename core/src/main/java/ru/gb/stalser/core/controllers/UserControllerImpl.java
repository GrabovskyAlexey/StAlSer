package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.stalser.api.dto.auth.*;
import ru.gb.stalser.core.controllers.interfaces.UserController;
import ru.gb.stalser.core.services.UserServiceImpl;

import javax.security.auth.message.AuthException;
import java.security.Principal;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserServiceImpl userService;

    @Override
    public ResponseEntity<AuthResponse> createAuthToken(@RequestBody AuthRequest authRequest) {

        return ResponseEntity.ok(userService.authenticate(authRequest));
    }

    @Override
    public AuthResponse register(final RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    @Override
    public ResponseEntity<AuthResponse> changePassword (@RequestBody AuthRequestPassUpdate authRequestPassUpdate, Principal principal){
        return ResponseEntity.ok(userService.registerPassUpdate(authRequestPassUpdate, principal));
    }

    @Override
    public ResponseEntity<AuthResponse> refresh(final RefreshRequest refreshRequest) throws AuthException {
        return ResponseEntity.ok(userService.refresh(refreshRequest.getRefreshToken()));
    }
}
