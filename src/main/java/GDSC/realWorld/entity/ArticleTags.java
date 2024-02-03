package GDSC.realWorld.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class ArticleTags {

    @Id @GeneratedValue
    private int articleId;

    @Id @GeneratedValue
    private int tagId;

}
