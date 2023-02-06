package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.auth.AuthRequest;
import ru.gb.stalser.api.dto.auth.AuthResponse;
import ru.gb.stalser.core.controllers.interfaces.AuthController;
import ru.gb.stalser.core.services.interfaces.UserService;
import ru.gb.stalser.core.utils.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<AuthResponse> createAuthToken(@RequestBody AuthRequest authRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getLogin());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
