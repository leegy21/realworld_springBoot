package GDSC.realWorld.repository;

import GDSC.realWorld.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    public Article findBySlug(String slug);
}
