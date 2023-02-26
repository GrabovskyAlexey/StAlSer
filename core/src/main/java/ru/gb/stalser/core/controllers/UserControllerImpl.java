package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.auth.*;
import ru.gb.stalser.core.controllers.interfaces.UserController;
import ru.gb.stalser.core.services.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Locale;

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
        return ResponseEntity.ok(userService.registerPassUpdate(authRequestPassUpdate, principal));
    }

    @Override
    public ResponseEntity<GenericResponse> resetPassword (@RequestBody String userNameForPasswordReset, HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(userService.resetPassword(httpServletRequest, userNameForPasswordReset));
    }

    @Override
    public ResponseEntity<String> showChangePasswordPage (@RequestBody String token, Locale locale, Model model){
        return ResponseEntity.ok(userService.showChangePasswordPage(locale, model, token));
    }

    @Override
    public ResponseEntity<AuthResponse> setNewPassword (@RequestBody RequestNewPass requestNewPass){
        return ResponseEntity.ok(userService.setNewPassword(requestNewPass));
    }


}
