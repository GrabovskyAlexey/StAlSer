package ru.gb.stalser.core.services.interfaces;

import org.springframework.data.domain.Page;
import ru.gb.stalser.core.entity.Sprint;


public interface SprintService {

    Page<Sprint> findAll(int pageIndex, int pageSize);

    Sprint findById(Long id);

    Sprint save(Sprint sprint);

    void update(Sprint sprint);

    void deleteById(Long id);

}
