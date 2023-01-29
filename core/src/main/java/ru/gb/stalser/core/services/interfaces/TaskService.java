package ru.gb.stalser.core.services.interfaces;

import ru.gb.stalser.core.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> findAll();

    Task findById(Long id);

    Task save(Task task);

    void update(Task task);

    void deleteById(Long id);
}
