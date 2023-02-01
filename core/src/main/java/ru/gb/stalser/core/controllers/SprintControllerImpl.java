package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.sprint.SprintDto;
import ru.gb.stalser.core.controllers.interfaces.SprintController;
import ru.gb.stalser.core.mappers.SprintMapper;
import ru.gb.stalser.core.services.interfaces.SprintService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${stalser.api.url}/sprints")
@RequiredArgsConstructor
public class SprintControllerImpl implements SprintController {

    private final SprintService sprintService;
    private final SprintMapper sprintMapper;

    @Override
    public ResponseEntity<List<SprintDto>> getAllSprints() {
        return ResponseEntity.ok(
                sprintService.findAll().stream()
                        .map(sprintMapper::mapToDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<SprintDto> getSprintById(final Long id) {
        return ResponseEntity.ok(sprintMapper
                .mapToDto(sprintService
                        .findById(id)));
    }

    @Override
    public ResponseEntity<SprintDto> addSprint(final SprintDto sprintDto) {
        return ResponseEntity.ok(sprintMapper.mapToDto(
                        sprintService.save(sprintMapper.mapFromDto(sprintDto))
                )
        );
    }

    @Override
    public void updateSprint(final Long id, final SprintDto sprintDto) {
        sprintService.update(sprintMapper.mapFromDto(sprintDto));
    }

    @Override
    public void deleteSprint(final Long id) {
        sprintService.deleteById(id);
    }

}
