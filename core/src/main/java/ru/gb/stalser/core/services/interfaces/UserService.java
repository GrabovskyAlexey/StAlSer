package ru.gb.stalser.core.services.interfaces;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.gb.stalser.api.dto.auth.AuthRequest;
import ru.gb.stalser.api.dto.auth.AuthResponse;
import ru.gb.stalser.core.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAll();

    User findById(Long id);

    User findByLogin(String login);

    User findByEmail(String email);

    User save(User user);

    void updateUser(User user);

    Boolean existsByEmail(String email);

    ResponseEntity<AuthResponse> authenticate(AuthRequest authRequest);
}
