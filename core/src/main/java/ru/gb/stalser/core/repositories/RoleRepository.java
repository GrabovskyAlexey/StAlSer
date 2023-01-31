package ru.gb.stalser.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.stalser.core.entity.Comment;

public interface RoleRepository  extends JpaRepository<Comment,Long> {

}
