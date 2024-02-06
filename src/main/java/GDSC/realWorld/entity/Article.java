package GDSC.realWorld.entity;

import GDSC.realWorld.domain.ArticleDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter @Getter
@RequiredArgsConstructor
public class Article {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER)
    private List<ArticleTags> articleTags;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authorId")
    @Column(nullable = false)
    private User user;

    public Article(ArticleDTO articleDTO, User user) {
        this.title = articleDTO.getTitle();
        this.description = articleDTO.getDescription();
        this.body = articleDTO.getBody();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.user = user;
    }

}
