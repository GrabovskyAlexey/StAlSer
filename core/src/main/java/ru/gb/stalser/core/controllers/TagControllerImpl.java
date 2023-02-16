package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.hibernate.ResourceClosedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.stalser.api.dto.tag.TagDto;
import ru.gb.stalser.core.controllers.interfaces.TagController;
import ru.gb.stalser.core.entity.Tag;
import ru.gb.stalser.core.mappers.TagMapper;
import ru.gb.stalser.core.services.interfaces.TagService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${stalser.api.url}/tag")
@RequiredArgsConstructor
public class TagControllerImpl implements TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @Override
    public ResponseEntity<List<TagDto>> getAllTags(Principal principal) {
        return ResponseEntity.ok(tagService.findAll().stream().map(tagMapper::mapToDto).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<TagDto> getTagById(Long id, Principal principal) {
        return ResponseEntity.ok(tagMapper.mapToDto(tagService.findById(id)));
    }

    @Override
    public ResponseEntity<TagDto> addTag(@Valid TagDto tag, Principal principal) {
        return ResponseEntity.ok(tagMapper.mapToDto(tagService.save(tagMapper.mapFromDto(tag))));
    }

    @Override
    public void updateTag(Long id, TagDto tagDto, Principal principal) {
        tagService.update(tagMapper.mapFromDto(tagDto));
    }

    @Override
    public void deleteTag(Long id, Principal principal) {
        tagService.deleteById(id);
    }
}
