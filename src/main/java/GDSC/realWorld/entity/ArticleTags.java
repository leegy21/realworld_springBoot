package GDSC.realWorld.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ArticleTags {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "articleId")
    private Article article;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tagId")
    private Tag tag;

}
