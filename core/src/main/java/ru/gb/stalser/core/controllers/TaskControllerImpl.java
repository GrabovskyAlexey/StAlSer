package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.tag.TagDto;
import ru.gb.stalser.api.dto.task.TaskDto;
import ru.gb.stalser.api.dto.util.MessageDto;
import ru.gb.stalser.core.controllers.interfaces.TaskController;
import ru.gb.stalser.core.mappers.TaskMapper;
import ru.gb.stalser.core.services.interfaces.TaskService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${stalser.api.url}/tasks")
@RequiredArgsConstructor
public class TaskControllerImpl implements TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Override
    public ResponseEntity<List<TaskDto>> getAllTasks(Principal principal) {
        return ResponseEntity.ok(
                taskService.findAll().stream()
                        .map(taskMapper::mapToDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<TaskDto> getTaskById(final Long id, Principal principal) {
        return ResponseEntity.ok(taskMapper.mapToDto(taskService.findById(id)));
    }

    @Override
    public ResponseEntity<List<TaskDto>> getAllTaskByTag(TagDto tagDto) {
        return ResponseEntity.ok(
                taskService.findAllTaskByTag(tagDto)
                        .stream()
                        .map(taskMapper::mapToDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<TaskDto> addTask(final TaskDto task, Principal principal) {
        return ResponseEntity.ok(
                taskMapper.mapToDto(
                        taskService.save(taskMapper.mapFromDto(task))
                )
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
