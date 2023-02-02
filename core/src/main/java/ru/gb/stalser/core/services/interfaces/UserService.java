package ru.gb.stalser.core.services.interfaces;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.gb.stalser.core.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAll();

    User findById(Long id);

    User findByLogin(String login);

    User save(User user);

    void updateUser(User user);
}
