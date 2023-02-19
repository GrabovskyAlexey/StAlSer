package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.stalser.api.dto.tag.TagDto;
import ru.gb.stalser.api.dto.user.UserDto;
import ru.gb.stalser.core.entity.Task;
import ru.gb.stalser.core.mappers.TagMapper;
import ru.gb.stalser.core.mappers.UserMapper;
import ru.gb.stalser.core.repositories.TaskRepository;
import ru.gb.stalser.core.services.interfaces.TaskService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private  final TagMapper tagMapper;
    private  final UserMapper userMapper;
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
    public Task save(final Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void update(final Task task) {
        taskRepository.save(task);
    }

    @Override
    public void deleteById(final Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> findAllTaskByTag(TagDto tag) {
        return taskRepository.findAllByTagsContaining(tagMapper.mapFromDto(tag));
    }

    @Override
    public List<Task> getTasksByUser(UserDto assigned) {
        return taskRepository.getTasksByAssigneeContaining(userMapper.mapFromDto(assigned));
    }




}
