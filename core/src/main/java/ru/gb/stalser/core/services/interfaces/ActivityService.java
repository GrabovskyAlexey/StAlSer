package ru.gb.stalser.core.services.interfaces;

import ru.gb.stalser.core.entity.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityService {
    List<Activity> findAll();

    Optional<Activity> findById(Long id);

    Activity save(Activity activity);

    Activity update(Activity activity);

    void deleteById(Long id);
}
