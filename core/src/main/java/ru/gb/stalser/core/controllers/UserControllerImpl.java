package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.auth.AuthRequest;
import ru.gb.stalser.api.dto.auth.AuthRequestPassUpdate;
import ru.gb.stalser.api.dto.auth.AuthResponse;
import ru.gb.stalser.api.dto.auth.RegisterRequest;
import ru.gb.stalser.core.controllers.interfaces.UserController;
import ru.gb.stalser.core.services.UserServiceImpl;

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
    public ResponseEntity<AuthResponse> register(final RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.register(registerRequest));
    }

    @Override
    public ResponseEntity<AuthResponse> changePassword (@RequestBody AuthRequestPassUpdate authRequestPassUpdate, Principal principal){
        return ResponseEntity.ok(userService.registerPassUpdate(authRequestPassUpdate));
    }
}
