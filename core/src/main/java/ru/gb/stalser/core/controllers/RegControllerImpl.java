package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.auth.AuthResponse;
import ru.gb.stalser.api.dto.auth.RegisterRequest;
import ru.gb.stalser.api.dto.user.UserDto;
import ru.gb.stalser.core.controllers.interfaces.RegController;
import ru.gb.stalser.core.repositories.RoleRepository;
import ru.gb.stalser.core.services.UserServiceImpl;
import ru.gb.stalser.core.services.interfaces.UserService;
import ru.gb.stalser.core.utils.JwtTokenUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/${stalser.api.url}/registration")
@RequiredArgsConstructor
public class RegControllerImpl implements RegController {

    private final UserServiceImpl userService;

    @Override
    public ResponseEntity<AuthResponse> regUser(@Valid RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.register(registerRequest));
    }
}
