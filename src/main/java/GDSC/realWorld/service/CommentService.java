package GDSC.realWorld.service;

import java.util.List;

import org.hibernate.annotations.Comments;
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
        return (List<Comment>) commentRepository.findByArticleSlug(slug);
    }

    public void deleteComment(Comment foundComment) {
        commentRepository.delete(foundComment);
    }

    public Comment findCommentBySlug(String slug) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findCommentBySlug'");
    }

}
