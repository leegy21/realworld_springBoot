package GDSC.realWorld.domain;

import java.time.LocalDateTime;
import java.util.List;

import GDSC.realWorld.entity.Article;
import GDSC.realWorld.entity.Comment;
import GDSC.realWorld.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String body;
    private User author;

    public CommentDTO(Comment comment) {
        this.id = comment.getid();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.body = comment.getBody();
        this.author = comment.getUser();
    }
}
