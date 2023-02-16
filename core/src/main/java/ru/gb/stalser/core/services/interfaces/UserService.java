package ru.gb.stalser.core.services.interfaces;


import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.gb.stalser.api.dto.auth.AuthRequest;
import ru.gb.stalser.api.dto.auth.AuthResponse;
import ru.gb.stalser.api.dto.auth.RegisterRequest;
import ru.gb.stalser.core.entity.User;


public interface UserService extends UserDetailsService {

    Page<User> findAll(int pageIndex, int pageSize);

    User findById(Long id);

    User findByLogin(String login);

    User findByEmail(String email);

    AuthResponse register(RegisterRequest registerRequest);

    void updateUser(User user);

    Boolean existsByEmail(String email);

    AuthResponse authenticate(AuthRequest authRequest);
}
