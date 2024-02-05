package GDSC.realWorld.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class ArticleTags {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "articleId")
    private Article article;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tagId")
    private Tag tag;

    public ArticleTags(Article article, Tag tag) {
        this.article = article;
        this.tag = tag;
    }
}
