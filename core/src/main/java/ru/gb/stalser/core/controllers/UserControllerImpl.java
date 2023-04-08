package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.stalser.api.dto.auth.*;
import ru.gb.stalser.api.dto.util.MessageDto;
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
    public AuthResponse activateUser(String confirmToken) {
        return userService.activateUser(confirmToken);
    }

    @Override
    public AuthResponse createAuthToken(@RequestBody AuthRequest authRequest) {

        return userService.authenticate(authRequest);
    }

    @Override
    public AuthResponse register(final RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    @Override
    public AuthResponse changePassword (@RequestBody AuthRequestPassUpdate authRequestPassUpdate, Principal principal){
        return userService.registerPassUpdate(authRequestPassUpdate, principal);
    }

    @Override
    public AuthResponse refresh(final RefreshRequest refreshRequest) throws AuthException {
        return userService.refresh(refreshRequest.getRefreshToken());
    }

    @Override
    public ResponseEntity<MessageDto> resetPassword (@RequestBody String userEmailForPasswordReset){
        return ResponseEntity.ok(userService.resetPassword(userEmailForPasswordReset));
    }

    @Override
    public ResponseEntity<AuthResponse> setNewPassword (@RequestBody RequestNewPass requestNewPass) throws AuthException {
        return ResponseEntity.ok(userService.setNewPassword(requestNewPass));
    }


}
