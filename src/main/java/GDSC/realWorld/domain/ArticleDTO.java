package GDSC.realWorld.domain;

import GDSC.realWorld.entity.Article;
import GDSC.realWorld.entity.Tag;
import GDSC.realWorld.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ArticleDTO {
    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> tagList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User author;

    public ArticleDTO(Article article, List<Tag> tagList) {
        this.slug = article.getSlug();
        this.title = article.getTitle();
        this.description = article.getDescription();
        this.body = article.getBody();
        this.createdAt = article.getCreatedAt();
        this.updatedAt = article.getUpdatedAt();
        this.author = article.getUser();
        this.tagList = Tag.getTagNameList(tagList);
    }
}
