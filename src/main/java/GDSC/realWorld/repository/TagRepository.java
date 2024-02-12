package GDSC.realWorld.repository;

import GDSC.realWorld.entity.Tag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAll();
}
