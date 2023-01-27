package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.gb.stalser.core.entity.Activity;
import ru.gb.stalser.core.repositories.ActivityRepository;
import ru.gb.stalser.core.services.interfaces.ActivityService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;

    @Override
    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    @Override
    public Activity findById(Long id) {
        return activityRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Activity id = " + id + " not found"));
    }

    @Override
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public Activity update(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public void deleteById(Long id) {
        activityRepository.deleteById(id);
    }
}
