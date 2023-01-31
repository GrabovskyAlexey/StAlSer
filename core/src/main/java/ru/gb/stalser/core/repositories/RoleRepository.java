package ru.gb.stalser.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.stalser.core.entity.Role;


public interface RoleRepository  extends JpaRepository<Role,Long> {

}
