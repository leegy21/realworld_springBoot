package GDSC.realWorld.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class ArticleTags {

    @EmbeddedId
    private ArticleTagId id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("articleId")
    @JoinColumn(name = "articleId")
    private Article article;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("tagId")
    @JoinColumn(name = "tagId")
    private Tag tag;

    public ArticleTags(Article article, Tag tag) {
        this.article = article;
        this.tag = tag;
    }
}
