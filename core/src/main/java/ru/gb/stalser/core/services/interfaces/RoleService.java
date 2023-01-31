package ru.gb.stalser.core.services.interfaces;


import ru.gb.stalser.core.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Role findById(Long id);

    Role save(Role comment);

    Role update(Role comment);

    void deleteById(Long id);

}
