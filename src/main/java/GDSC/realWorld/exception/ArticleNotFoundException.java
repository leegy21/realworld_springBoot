package GDSC.realWorld.exception;

public class ArticleNotFoundException extends RuntimeException{
    public ArticleNotFoundException() {
        super("Article Not Found");
    }
}
