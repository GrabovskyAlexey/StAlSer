package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.sprint.SprintDto;
import ru.gb.stalser.api.dto.tag.TagDto;
import ru.gb.stalser.api.dto.task.TaskDto;
import ru.gb.stalser.api.dto.task.TaskListResponse;
import ru.gb.stalser.api.dto.user.UserDto;
import ru.gb.stalser.core.controllers.interfaces.TaskController;
import ru.gb.stalser.core.mappers.TaskMapper;
import ru.gb.stalser.core.services.interfaces.TaskService;

import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${stalser.api.url}/tasks")
@RequiredArgsConstructor
public class TaskControllerImpl implements TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Override
    public TaskListResponse getAllTasks(Principal principal) {
        return TaskListResponse.builder()
                .tasks(
                        taskService.findAll().stream()
                                .map(taskMapper::mapToDto)
                                .collect(Collectors.toList()))
                .build();
    }

    @Override
    public TaskDto getTaskById(final Long id, Principal principal) {
        return taskMapper.mapToDto(taskService.findById(id));
    }

    @Override
    public TaskListResponse getAllTaskByTag(TagDto tagDto, Principal principal) {
        return TaskListResponse.builder()
                .tasks(
                        taskService.findAllTaskByTag(tagDto)
                                .stream()
                                .map(taskMapper::mapToDto)
                                .collect(Collectors.toList()))
                .build();
    }
    @Override
    public TaskListResponse getTasksByUser(final UserDto user, Principal principal) {
        return TaskListResponse.builder()
                .tasks(
                        taskService.getTasksByUser(user).stream()
                                .map(taskMapper::mapToDto)
                                .collect(Collectors.toList()))
                .build();
    }

    @Override
    public TaskListResponse getTasksBySprint(final SprintDto sprint, Principal principal) {
        return TaskListResponse.builder()
                .tasks(
                        taskService.getTasksBySprint(sprint).stream()
                                .map(taskMapper::mapToDto)
                                .collect(Collectors.toList()))
                .build();
    }


    @Override
    public TaskDto addTask(final TaskDto task, Principal principal) {
        return taskMapper.mapToDto(
                taskService.save(taskMapper.mapFromDto(task))
        );
    }

    @Override
    public void updateTask(final Long id, final TaskDto task, Principal principal) {
        taskService.update(taskMapper.mapFromDto(task));
    }

    @Override
    public void deleteTask(final Long id, Principal principal) {
        taskService.deleteById(id);
    }
}
