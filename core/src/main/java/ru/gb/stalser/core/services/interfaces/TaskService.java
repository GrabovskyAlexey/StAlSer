package ru.gb.stalser.core.services.interfaces;

import org.springframework.data.domain.Page;
import ru.gb.stalser.core.entity.Task;


public interface TaskService {
    Page<Task> findAll(int pageIndex, int pageSize);

    Task findById(Long id);

    Task save(Task task);

    void update(Task task);

    void deleteById(Long id);
}
