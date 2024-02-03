package GDSC.realWorld.service;

import GDSC.realWorld.domain.ArticleDTO;
import GDSC.realWorld.entity.Article;
import GDSC.realWorld.entity.User;
import GDSC.realWorld.exception.ArticleNotFoundException;
import GDSC.realWorld.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
}
