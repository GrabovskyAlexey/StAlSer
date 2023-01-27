package ru.gb.stalser.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.stalser.core.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
