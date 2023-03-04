package ru.gb.stalser.core.services.interfaces;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.gb.stalser.api.dto.ConfirmToken;
import ru.gb.stalser.api.dto.auth.*;
import ru.gb.stalser.core.entity.User;

import java.security.Principal;
import javax.security.auth.message.AuthException;
import java.security.Principal;
import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAll();

    User findById(Long id);

    User findByLogin(String login);

    User findByEmail(String email);

    AuthResponse register(RegisterRequest registerRequest);

    void updateUser(User user);

    Boolean existsByEmail(String email);

    AuthResponse authenticate(AuthRequest authRequest);

    AuthResponse registerPassUpdate(AuthRequestPassUpdate authRequestPassUpdate, Principal principal);

    AuthResponse refresh(String refreshToken) throws AuthException;

    User getUserFromPrincipal(Principal principal);

    ActivateResponse activateUser(ConfirmToken token);

}
