package ru.gb.stalser.core.services.interfaces;

import org.springframework.data.domain.Page;
import ru.gb.stalser.core.entity.Activity;

import java.util.Optional;

public interface ActivityService {
    Page<Activity> findAll(int pageIndex, int pageSize);

    Activity findById(Long id);

    Activity save(Activity activity);

    Activity update(Activity activity);

    void deleteById(Long id);
}
