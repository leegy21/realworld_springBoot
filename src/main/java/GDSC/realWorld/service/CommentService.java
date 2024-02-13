package GDSC.realWorld.service;

import java.util.List;

import org.springframework.stereotype.Service;

import GDSC.realWorld.domain.CommentDTO;
import GDSC.realWorld.entity.Article;
import GDSC.realWorld.entity.Comment;
import GDSC.realWorld.entity.User;
import GDSC.realWorld.exception.ArticleNotFoundException;
import GDSC.realWorld.repository.ArticleRepository;
import GDSC.realWorld.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    //final로 선언해야 @RequiredArgsConstructor에 의해 생성자가 만들어져서 Bean 주입 받음

    public Comment addCommentToArticle(String slug, CommentDTO commentDTO) throws ArticleNotFoundException{
        Article article = articleRepository.findBySlug(slug);
        if (article == null){
            throw new ArticleNotFoundException();
        }
        Comment comment = new Comment();
        comment.setBody(commentDTO.getBody());
        comment.setArticle(article);

        commentRepository.save(comment);
        
        return comment;
    }

    public List<Comment> getCommentsByArticleSlug(String slug) {
        return (List<Comment>) commentRepository.findBySlug(slug);
        /*
        - Comment 엔티티에는 Slug 필드가 없음
        - 해당 findBySlug은 단일 Comment를 Return 하는데 왜 List로 선언 되어 있는 것인지?
         */

    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

}
