package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.auth.AuthRequest;
import ru.gb.stalser.api.dto.auth.AuthResponse;
import ru.gb.stalser.core.controllers.interfaces.UserController;
import ru.gb.stalser.core.services.UserServiceImpl;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserServiceImpl userService;

    @Override
    public ResponseEntity<AuthResponse> createAuthToken(@RequestBody AuthRequest authRequest) {

        return userService.authenticate(authRequest);
    }
}
