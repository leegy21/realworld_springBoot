package GDSC.realWorld.controller;

import GDSC.realWorld.domain.ArticleDTO;
import GDSC.realWorld.domain.ArticleWrapper;
import GDSC.realWorld.entity.Article;
import GDSC.realWorld.entity.Tag;
import GDSC.realWorld.entity.User;
import GDSC.realWorld.exception.ArticleNotFoundException;
import GDSC.realWorld.service.ArticleService;
import GDSC.realWorld.service.ArticleTagService;
import GDSC.realWorld.service.TagService;
import GDSC.realWorld.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity listArticles(@RequestParam String tag,
                                       @RequestParam String author,
                                       @RequestParam String favorited,
                                       @RequestParam @DefaultValue("20") int limit,
                                       @RequestParam @DefaultValue("0") int offset) {
        return null;
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
}
