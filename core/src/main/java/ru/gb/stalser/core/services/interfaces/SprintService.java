package ru.gb.stalser.core.services.interfaces;

import ru.gb.stalser.core.entity.Sprint;

import java.util.List;

public interface SprintService {

    List<Sprint> findAll();

    Sprint findById(Long id);

    Sprint save(Sprint sprint);

    void update(Sprint sprint);

    void deleteById(Long id);

}
