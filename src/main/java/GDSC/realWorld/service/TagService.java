package GDSC.realWorld.service;

import GDSC.realWorld.entity.Tag;
import GDSC.realWorld.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public void createTag(String name) {
        Tag tag = new Tag(name);
        tagRepository.save(tag);
    }

}

