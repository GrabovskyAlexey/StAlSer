package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.activity.ActivityDto;
import ru.gb.stalser.api.dto.activity.ActivityListResponse;
import ru.gb.stalser.core.controllers.interfaces.ActivityController;
import ru.gb.stalser.core.mappers.ActivityMapper;
import ru.gb.stalser.core.services.interfaces.ActivityService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${stalser.api.url}/activity")
@RequiredArgsConstructor
public class ActivityControllerImpl implements ActivityController {
    private final ActivityService activityService;
    private final ActivityMapper activityMapper;

    @Override
    public ActivityListResponse getAllActivities(Principal principal) {
        return ActivityListResponse.builder()
                .activities(
                        activityService.findAll().stream()
                                .map(activityMapper::mapToDto)
                                .collect(Collectors.toList()))
                .build();
    }

    @Override
    public ActivityDto getActivityById(Long id, Principal principal) {
        return activityMapper.mapToDto(activityService.findById(id));
    }

    @Override
    public ActivityDto addActivity(ActivityDto activity, Principal principal) {
        return activityMapper.mapToDto(activityService.save(activityMapper.mapFromDto(activity)));
    }

    @Override
    public void updateActivity(Long id, ActivityDto activity, Principal principal) {
        activityService.update(activityMapper.mapFromDto(activity));
    }

    @Override
    public void deleteActivity(Long id, Principal principal) {
        activityService.deleteById(id);
    }
}
