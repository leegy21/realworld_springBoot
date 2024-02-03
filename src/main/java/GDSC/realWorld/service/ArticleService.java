package GDSC.realWorld.service;

import GDSC.realWorld.domain.ArticleDTO;
import GDSC.realWorld.entity.Article;
import GDSC.realWorld.entity.User;
import GDSC.realWorld.exception.ArticleNotFoundException;
import GDSC.realWorld.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article createArticle(ArticleDTO articleDTO, User user) {
        Article article = new Article(articleDTO, user);
        articleRepository.save(article);
        return article;
    }

    public Article findArticleBySlug(String slug) {
        return Optional.ofNullable(articleRepository.findBySlug(slug)).orElseThrow(ArticleNotFoundException::new);
    }

    @Transactional
    public void updateArticle(Article article, ArticleDTO articleDTO, String slug) {
        if (articleDTO.getTitle() != null) {
            article.setSlug(slug);
            article.setTitle(article.getTitle());
        }
        if (articleDTO.getDescription() != null) {
            article.setDescription(article.getDescription());
        }
        if (articleDTO.getBody() != null) {
            article.setBody(articleDTO.getBody());
        }
    }
}
