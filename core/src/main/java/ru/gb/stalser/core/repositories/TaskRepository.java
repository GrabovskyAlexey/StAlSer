package ru.gb.stalser.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.stalser.core.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}