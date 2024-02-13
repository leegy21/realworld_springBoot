package GDSC.realWorld.controller;

import GDSC.realWorld.domain.ArticleDTO;
import GDSC.realWorld.domain.ArticleWrapper;
import GDSC.realWorld.domain.CommentDTO;
import GDSC.realWorld.domain.CommentWrapper;
import GDSC.realWorld.entity.*;
import GDSC.realWorld.exception.ArticleNotFoundException;
import GDSC.realWorld.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

    private final UserService userService;
    private final ArticleService articleService;
    private final TagService tagService;
    private final ArticleTagService articleTagService;
    private final CommentService commentService;
    private final FavoriteService favoriteService;

    @GetMapping("/articles")
    public ResponseEntity<Page<ArticleDTO>> getArticles(
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String username,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "20") int limit) {

        Page<ArticleDTO> articles = articleService.getListArticles(tagName, username, offset, limit);
        Map<String, Object> response = new HashMap<>();
        response.put("articles", articles.getContent());
        response.put("articlesCount", articles.getTotalElements());

        return new ResponseEntity(response, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity createArticle(@RequestBody ArticleWrapper articleWrapper,
                                        HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        //세션에 Username를 담도록 해야됨

        String username = (String) session.getAttribute("user");
        User findUser = userService.findByUsername(username);

        Article article = articleService.createArticle(articleWrapper.getArticle(), findUser);

        List<Tag> tagList = tagService.createTagListByTagNameList(articleWrapper.getArticle().getTagList());
        articleTagService.createArticleTagByTagListAndArticle(article, tagList);

        ArticleDTO articleDTO = new ArticleDTO(article, tagService.getTagNameListByTagList(tagList));
        Map<String, Object> response = new HashMap<>();
        response.put("article", articleDTO);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/{slug}")
    public ResponseEntity getArticle(@PathVariable String slug) {
        try {
            Article article = articleService.findArticleBySlug(slug);
            List<String> tagNameList = articleTagService.findTagNameListByArticle(article);
            ArticleDTO articleResponse = new ArticleDTO(article, tagNameList);
            Map<String, Object> response = new HashMap<>();
            response.put("article", articleResponse);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{slug}")
    public ResponseEntity updateArticle(@PathVariable String slug,
                                        @RequestBody ArticleWrapper articleWrapper) {
        ArticleDTO articleDTO = articleWrapper.getArticle();
        try {
            Article foundArticle = articleService.findArticleBySlug(slug);
            articleService.updateArticle(foundArticle, articleDTO, slug);
            ArticleDTO articleResponse = new ArticleDTO(foundArticle, articleTagService.findTagNameListByArticle(foundArticle));
            Map<String, Object> response = new HashMap<>();
            response.put("article", articleResponse);
            return new ResponseEntity(foundArticle, HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity deleteArticle(@PathVariable String slug) {
        try {
            Article foundArticle = articleService.findArticleBySlug(slug);
            articleService.deleteArticle(foundArticle);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/{slug}/comments")
    public ResponseEntity addCommentToArticle(@PathVariable String slug, @RequestBody CommentWrapper commentWrapper) {
        CommentDTO commentDTO = commentWrapper.getComment();

        try {
            Comment savedComment = commentService.addCommentToArticle(slug, commentDTO);
            CommentDTO savedCommentDTO = new CommentDTO(savedComment);
            //CommentDTO를 요청에서 받는데 새로 CommentDTO를 만드는 이유가 무엇인지?
            Map<String, CommentDTO> response = new HashMap<>();
            response.put("comment", savedCommentDTO);
            return new ResponseEntity(savedComment, HttpStatus.CREATED);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{slug}/comments")
    public ResponseEntity<Map<String, List<CommentDTO>>> getCommentsByArticleSlug(@PathVariable String slug) {
        List<Comment> comments = commentService.getCommentsByArticleSlug(slug);
        List<CommentDTO> commentDTOs = new ArrayList<>();

        for (Comment comment : comments) {
            CommentDTO commentDTO = new CommentDTO(comment);
            commentDTOs.add(commentDTO);
        }

        Map<String, List<CommentDTO>> response = new HashMap<>();
        response.put("comments", commentDTOs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{slug}/comments/{id}")
    public ResponseEntity deleteComment(@PathVariable String slug) {
        try {
            Article foundArticle = articleService.findArticleBySlug(slug);
            articleService.deleteArticle(foundArticle);
            //Comment를 지워야 하는데 Article을 삭제해버림
            return new ResponseEntity(HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{slug}/favorite")
    public ResponseEntity<Map<String, ArticleDTO>> favoriteArticle(@PathVariable String slug, HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        Article article = articleService.findArticleBySlug(slug);

        Favorite existingFavorite = favoriteService.findByUserAndArticle(user, article);
        if (existingFavorite == null) {
            favoriteService.addFavorite(user, article);
        }

        ArticleDTO articleDTO = new ArticleDTO(article, user);
        Map<String, ArticleDTO> response = new HashMap<>();
        response.put("article", articleDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{slug}/favorite")
    public ResponseEntity<Map<String, ArticleDTO>> unfavoriteArticle(@PathVariable String slug, HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        Article article = articleService.findArticleBySlug(slug);

        Favorite existingFavorite = favoriteService.findByUserAndArticle(user, article);
        if (existingFavorite != null) {
            favoriteService.removeFavorite(user, article);
        }

        article = articleService.findArticleBySlug(slug);
        ArticleDTO articleDTO = new ArticleDTO(article, user);

        Map<String, ArticleDTO> response = new HashMap<>();
        response.put("article", articleDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
