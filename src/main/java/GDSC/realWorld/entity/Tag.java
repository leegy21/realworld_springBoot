package GDSC.realWorld.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@RequiredArgsConstructor
public class Tag {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public static List<String> getTagNameList(List<Tag> tagList) {
        return tagList.stream().map(Tag::getName).collect(Collectors.toList());
    }
}
