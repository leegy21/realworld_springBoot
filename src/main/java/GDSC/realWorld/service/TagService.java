package GDSC.realWorld.service;

import GDSC.realWorld.entity.Tag;
import GDSC.realWorld.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> createTagListByTagNameList(List<String> tagNameList) {
        return tagNameList.stream()
                .map(Tag::new)
                .peek(tagRepository::save)
                .collect(Collectors.toList());
    }

    public List<String> getTagNameListByTagList(List<Tag> tagList) {
        return tagList.stream().map(Tag::getName).collect(Collectors.toList());
    }

}

