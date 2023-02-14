package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.gb.stalser.core.entity.Activity;
import ru.gb.stalser.core.repositories.ActivityRepository;
import ru.gb.stalser.core.services.interfaces.ActivityService;

import javax.persistence.EntityNotFoundException;


@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;

    @Override
    public Page<Activity> findAll(int pageIndex, int pageSize) {
        return activityRepository.findAll(PageRequest.of(pageIndex, pageSize));
    }

    @Override
    public Activity findById(Long id) {
        return activityRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Activity id = " + id + " not found"));
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
