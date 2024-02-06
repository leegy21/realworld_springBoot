package GDSC.realWorld.service;

import GDSC.realWorld.entity.Article;
import GDSC.realWorld.entity.ArticleTags;
import GDSC.realWorld.entity.Tag;
import GDSC.realWorld.repository.ArticleTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleTagService {

    private final ArticleTagRepository articleTagRepository;

    public List<ArticleTags> findArticleTagListByArticle(Article article) {
        return articleTagRepository.findArticleTagsByArticle(article);
    }

    public void createArticleTagByTagListAndArticle(Article article, List<Tag> tagList) {
        tagList.stream().forEach(tag -> {
            ArticleTags articleTag = new ArticleTags(article, tag);
            articleTagRepository.save(articleTag);
        });
    }

    public List<String> findTagNameListByArticle(Article article) {
        return findArticleTagListByArticle(article).stream().map(ArticleTags::getTag).map(Tag::getName).collect(Collectors.toList());
    }

}
