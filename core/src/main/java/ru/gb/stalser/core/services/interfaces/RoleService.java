package ru.gb.stalser.core.services.interfaces;


import org.springframework.data.domain.Page;
import ru.gb.stalser.core.entity.Role;

import java.util.Optional;

public interface RoleService {
    Page<Role> findAll(int pageIndex, int pageSize);

    Role findById(Long id);

    Role save(Role role);

    Role update(Role role);

    void deleteById(Long id);

    Optional<Role> findByName(String roleUser);
}
