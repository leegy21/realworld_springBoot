package GDSC.realWorld.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ArticleTags {

    @EmbeddedId
    private ArticleTagId id;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("articleId")
    @JoinColumn(name = "article_Id")
    private Article article;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("tagId")
    @JoinColumn(name = "tag_Id")
    private Tag tag;

    public ArticleTags(Article article, Tag tag) {
        this.id = new ArticleTagId(article.getId(), tag.getId());
        this.article = article;
        this.tag = tag;
    }
}
