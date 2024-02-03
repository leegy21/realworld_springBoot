package GDSC.realWorld.controller;

import GDSC.realWorld.domain.ArticleWrapper;
import GDSC.realWorld.entity.Article;
import GDSC.realWorld.entity.User;
import GDSC.realWorld.service.ArticleService;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

    private final UserService userService;
    private final ArticleService articleService;
    private final TagService tagService;

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
        Article article = articleService.createArticle(articleWrapper.getArticleDTO(), findUser);
        List<String> tagList = articleWrapper.getArticleDTO().getTagList();
        tagList.stream().forEach(tagName -> tagService.createTag(tagName));
        return new ResponseEntity(article, HttpStatus.OK);
    }
}
