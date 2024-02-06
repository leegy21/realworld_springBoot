package GDSC.realWorld.service;

import GDSC.realWorld.domain.ArticleDTO;
import GDSC.realWorld.entity.Article;
import GDSC.realWorld.entity.ArticleTags;
import GDSC.realWorld.entity.User;
import GDSC.realWorld.exception.ArticleNotFoundException;
import GDSC.realWorld.repository.ArticleRepository;
import GDSC.realWorld.repository.ArticleTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleTagRepository articleTagRepository;
    private final UserService userService;

    public Article createArticle(ArticleDTO articleDTO, User user) {
        Article article = new Article(articleDTO, user);
        articleRepository.save(article);
        return article;
    }

    public void deleteArticle(Article article) {
        articleRepository.delete(article);
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

    public Page<ArticleDTO> getListArticles(String tagName, String username, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by("createdAt").descending());

        List<Article> listArticles;
        if (tagName != null && username != null) {
            listArticles = getArticlesByTagAndAuthor(tagName, username);
        } else if (tagName != null) {
            listArticles = getArticlesByTagName(tagName);
        } else if (username != null) {
            listArticles = getArticlesByAuthor(username, pageable);
        } else {
            listArticles = getAllArticles(pageable);
        }

        List<ArticleDTO> articleDTOs = listArticles.stream()
                .map(article -> new ArticleDTO(article, getTagNames(article)))
                .collect(Collectors.toList());

        return new PageImpl<>(articleDTOs, pageable, listArticles.size());
    }

    private List<Article> getArticlesByTagAndAuthor(String tagName, String username) {
        User author = userService.findByUsername(username);
        List<ArticleTags> articleTagsList = articleTagRepository.findByTag_Name(tagName);
        return articleTagsList.stream()
                .map(ArticleTags::getArticle)
                .filter(article -> article.getUser().equals(author))
                .collect(Collectors.toList());
    }

    private List<Article> getArticlesByTagName(String tagName) {
        List<ArticleTags> articleTagsList = articleTagRepository.findByTag_Name(tagName);
        return articleTagsList.stream().map(ArticleTags::getArticle).collect(Collectors.toList());
    }

    private List<Article> getArticlesByAuthor(String username, Pageable pageable) {
        User author = userService.findByUsername(username);
        return articleRepository.findByUser(author, pageable).getContent();
    }

    private List<Article> getAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable).getContent();
    }

    private List<String> getTagNames(Article article) {
        return article.getArticleTags().stream()
                .map(articleTag -> articleTag.getTag().getName())
                .collect(Collectors.toList());
    }


}
