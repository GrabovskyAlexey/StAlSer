package ru.gb.stalser.core.services.interfaces;


import ru.gb.stalser.core.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll();

    Role findById(Long id);

    Role save(Role role);

    Role update(Role role);

    void deleteById(Long id);

    Optional<Role> findByName(String roleUser);
}
