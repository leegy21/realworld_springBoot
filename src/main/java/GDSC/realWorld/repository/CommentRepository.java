package GDSC.realWorld.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import GDSC.realWorld.entity.Comment;
import GDSC.realWorld.entity.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //Page<Comment> findByUser(User user, Pageable pageable);
    List<Comment> findByArticleSlug(String slug);
}