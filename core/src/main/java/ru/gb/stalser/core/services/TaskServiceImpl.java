package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.stalser.core.entity.Task;
import ru.gb.stalser.core.repositories.TaskRepository;
import ru.gb.stalser.core.services.interfaces.TaskService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private  final TaskRepository taskRepository;
    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task findById(final Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Task id=" + id + "{} not found")
        );
    }

    @Override
    public void save(final Task task) {
        taskRepository.save(task);
    }

    @Override
    public void update(final Task task) {
        taskRepository.save(task);
    }

    @Override
    public void deleteById(final Long id) {
        taskRepository.deleteById(id);
    }
}