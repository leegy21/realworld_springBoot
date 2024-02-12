package GDSC.realWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import GDSC.realWorld.entity.Article;
import GDSC.realWorld.entity.Favorite;
import GDSC.realWorld.entity.User;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Favorite findByUserAndArticle(User user, Article article);
}
