package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.gb.stalser.core.entity.Tag;
import ru.gb.stalser.core.repositories.TagRepository;
import ru.gb.stalser.core.services.interfaces.TagService;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public Page<Tag> findAll(int pageIndex, int pageSize) {
       return tagRepository.findAll(PageRequest.of(pageIndex, pageSize));
    }

    @Override
    public Tag findById(Long id) {
       return tagRepository.findById(id)
               .orElseThrow(()-> new EntityNotFoundException("Tag id = "  + id + " not found!"));
    }

    @Override
    public Tag save(Tag tag) {
     return tagRepository.save(tag);
    }

    @Override
    public void update(Tag tag) {
       tagRepository.save(tag);
    }

    @Override
    public void deleteById(Long id) {
       tagRepository.deleteById(id);
    }
}
