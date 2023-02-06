package ru.gb.stalser.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.stalser.core.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
