package GDSC.realWorld.service;

import GDSC.realWorld.domain.ArticleDTO;
import GDSC.realWorld.entity.Article;
import GDSC.realWorld.entity.User;
import GDSC.realWorld.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article createArticle(ArticleDTO articleDTO, User user) {
        Article article = new Article(articleDTO, user);
        articleRepository.save(article);
        return article;
    }
}
