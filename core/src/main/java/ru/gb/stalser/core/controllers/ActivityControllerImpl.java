package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.activity.ActivityDto;
import ru.gb.stalser.core.controllers.interfaces.ActivityController;
import ru.gb.stalser.core.mappers.ActivityMapper;
import ru.gb.stalser.core.services.interfaces.ActivityService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${stalser.api.url}/activity")
@RequiredArgsConstructor
public class ActivityControllerImpl implements ActivityController {
    private final ActivityService activityService;
    private final ActivityMapper activityMapper;
    @Override
    public ResponseEntity<List<ActivityDto>> getAllActivities() {
        return ResponseEntity.ok(activityService.findAll().stream().map(activityMapper::mapToDto).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<ActivityDto> getActivityById(Long id) {
        return ResponseEntity.ok(activityMapper.mapToDto(activityService.findById(id)));
    }

    @Override
    public ResponseEntity<ActivityDto> addActivity(ActivityDto activity) {
        return ResponseEntity.ok(activityMapper.mapToDto(activityService.save(activityMapper.mapFromDto(activity))));
    }

    @Override
    public void updateActivity(Long id, ActivityDto activity) {
        activityService.update(activityMapper.mapFromDto(activity));
    }
    @Override
    public void deleteActivity(Long id) {
        activityService.deleteById(id);
    }
}
