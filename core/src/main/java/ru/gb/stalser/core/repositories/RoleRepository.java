package ru.gb.stalser.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.stalser.core.entity.Role;

import java.util.Optional;


public interface RoleRepository  extends JpaRepository<Role,Long> {


    Optional<Role> findRoleByRoleName(String roleUser);
}
