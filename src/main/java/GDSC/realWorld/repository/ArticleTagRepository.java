package GDSC.realWorld.repository;

import GDSC.realWorld.entity.Article;
import GDSC.realWorld.entity.ArticleTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTags, Article> {
    List<ArticleTags> findArticleTagsByArticle(Article article);

    List<ArticleTags> findByTag_Name(String tagName);
}
