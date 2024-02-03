package GDSC.realWorld.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleDTO {
    private String title;
    private String description;
    private String body;
    private List<String> tagList;
}
