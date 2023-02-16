package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.gb.stalser.core.entity.Sprint;
import ru.gb.stalser.core.repositories.SprintRepository;
import ru.gb.stalser.core.services.interfaces.SprintService;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor

public class SprintServiceImpl implements SprintService {

    private final SprintRepository sprintRepository;

    @Override
    public Page<Sprint> findAll (int pageIndex, int pageSize){
        return sprintRepository.findAll(PageRequest.of(pageIndex, pageSize));
    }

    @Override
    public Sprint findById (Long id){
        return sprintRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Sprint id = " + id + " not found"));
    }

    @Override
    public Sprint save (Sprint sprint){
        return sprintRepository.save(sprint);
    }

    @Override
    public void update (Sprint sprint){
        sprintRepository.save(sprint);
    }

    @Override
    public void deleteById (Long id){
        sprintRepository.deleteById(id);
    }

}
