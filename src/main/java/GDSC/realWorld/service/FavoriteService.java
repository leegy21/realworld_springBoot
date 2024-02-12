package GDSC.realWorld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import GDSC.realWorld.entity.Article;
import GDSC.realWorld.entity.Favorite;
import GDSC.realWorld.entity.User;
import GDSC.realWorld.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    public Favorite addFavorite(User user, Article article) {
        Favorite existingFavorite = favoriteRepository.findByUserAndArticle(user, article);
        if (existingFavorite == null) {
            Favorite favorite = new Favorite();
            favorite.setUser(user);
            favorite.setArticle(article);
            return favoriteRepository.save(favorite);
        } else {
            return existingFavorite;
        }
    }

    public void removeFavorite(User user, Article article) {
        Favorite existingFavorite = favoriteRepository.findByUserAndArticle(user, article);
        if (existingFavorite != null) {
            favoriteRepository.delete(existingFavorite);
        }
    }

    public Favorite findByUserAndArticle(User user, Article article) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUserAndArticle'");
    }
}
