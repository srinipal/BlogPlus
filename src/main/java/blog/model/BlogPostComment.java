package blog.model;

/**
 * Created by srinivas.g on 22/11/16.
 */
public class BlogPostComment {
    private String body;
    private String email;
    private String author;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BlogPostComment(String body, String email, String author) {

        this.body = body;
        this.email = email;
        this.author = author;
    }
}
