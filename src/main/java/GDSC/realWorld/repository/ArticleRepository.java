package GDSC.realWorld.repository;

import GDSC.realWorld.entity.Article;
import GDSC.realWorld.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findBySlug(String slug);
    Page<Article> findByUser(User user, Pageable pageable);

}
