package ru.gb.stalser.core.services.interfaces;

import ru.gb.stalser.core.entity.Tag;
import ru.gb.stalser.core.entity.Task;

import java.util.List;

public interface TagService {

    List<Tag> findAll();

    Tag findById(Long id);

    Tag save(Tag tag);

    void update(Tag tag);

    void deleteById(Long id);

}
