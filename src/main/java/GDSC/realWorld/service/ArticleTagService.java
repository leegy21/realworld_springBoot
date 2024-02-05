package GDSC.realWorld.service;

import GDSC.realWorld.entity.Article;
import GDSC.realWorld.entity.ArticleTags;
import GDSC.realWorld.entity.Tag;
import GDSC.realWorld.repository.ArticleRepository;
import GDSC.realWorld.repository.ArticleTagRepository;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleTagService {

    private final ArticleTagRepository articleTagRepository;

    public List<ArticleTags> findArticleTagsByArticle(Article article) {
        return articleTagRepository.findArticleTagsByArticle(article);
    }

    public void createArticleTagByTagListAndArticle(Article article, List<Tag> tagList) {
        tagList.stream().forEach(tag -> {
            ArticleTags articleTag = new ArticleTags(article, tag);
            articleTagRepository.save(articleTag);
        });
    }

    public List<Tag> getTagListByArticle(Article article) {
        List<ArticleTags> articleTagList = articleTagRepository.findArticleTagsByArticle(article);
        return articleTagList.stream().map(ArticleTags::getTag).collect(Collectors.toList());
    }

}
