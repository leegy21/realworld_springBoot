package GDSC.realWorld.repository;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import GDSC.realWorld.entity.Comment;
import GDSC.realWorld.entity.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findBySlug(String slug);
    //Comment 엔티티에는 Slug 필드가 없는데 어떻게 찾는 것인지?

    Page<Comment> findByUser(User user, Pageable pageable);

}