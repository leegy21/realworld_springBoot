package GDSC.realWorld.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ArticleTagId implements Serializable {
    public Long articleId;
    public Long tagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleTagId that = (ArticleTagId) o;
        return Objects.equals(articleId, that.articleId) && Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId, tagId);
    }

    public ArticleTagId(Long articleId, Long tagId) {
        this.articleId = articleId;
        this.tagId = tagId;
    }
}
