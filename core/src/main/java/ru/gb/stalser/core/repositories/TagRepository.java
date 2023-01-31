package ru.gb.stalser.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.stalser.core.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
