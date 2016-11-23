package blog.model;

/**
 * Created by srinivas.g on 22/11/16.
 */
public class BlogPostComment {
    private String body;
    private String author;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BlogPostComment(String body, String author) {

        this.body = body;
        this.author = author;
    }
}
