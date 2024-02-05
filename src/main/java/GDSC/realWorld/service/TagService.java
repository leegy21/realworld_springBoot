package GDSC.realWorld.service;

import GDSC.realWorld.entity.Tag;
import GDSC.realWorld.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> createTagListByTagNameList(List<String> tagNameList) {
        List<Tag> tagList = new ArrayList<>();
        tagNameList.stream().forEach(tagName -> {
            Tag tag = new Tag(tagName);
            tagList.add(tag);
            tagRepository.save(tag);
        });
        return tagList;
    }

}

