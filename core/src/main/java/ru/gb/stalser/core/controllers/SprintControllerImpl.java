package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.sprint.SprintDto;
import ru.gb.stalser.api.dto.sprint.SprintListResponse;
import ru.gb.stalser.core.controllers.interfaces.SprintController;
import ru.gb.stalser.core.mappers.SprintMapper;
import ru.gb.stalser.core.services.interfaces.SprintService;

import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${stalser.api.url}/sprints")
@RequiredArgsConstructor
public class SprintControllerImpl implements SprintController {

    private final SprintService sprintService;
    private final SprintMapper sprintMapper;

    @Override
    public SprintListResponse getAllSprints(Principal principal) {
        return SprintListResponse.builder()
                .sprints(
                        sprintService.findAll().stream()
                                .map(sprintMapper::mapToDto)
                                .collect(Collectors.toList()))
                .build();
    }

    @Override
    public SprintDto getSprintById(final Long id, Principal principal) {
        return sprintMapper
                .mapToDto(sprintService
                        .findById(id));
    }

    @Override
    public SprintDto addSprint(final SprintDto sprintDto, Principal principal) {
        return sprintMapper.mapToDto(
                sprintService.save(sprintMapper.mapFromDto(sprintDto))
        );
    }

    @Override
    public void updateSprint(final Long id, final SprintDto sprintDto, Principal principal) {
        sprintService.update(sprintMapper.mapFromDto(sprintDto));
    }

    @Override
    public void deleteSprint(final Long id, Principal principal) {
        sprintService.deleteById(id);
    }

}
