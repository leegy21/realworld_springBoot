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
    private ArticleRepository articleRepository;

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
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

}
