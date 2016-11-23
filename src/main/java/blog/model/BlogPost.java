package blog.model;

/**
 * Created by srinivas.g on 18/11/16.
 */
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogPost {
    public BlogPost(String title, String author, String body, List<String> tags) {
        this.title = title;
        this.author = author;
        this.body = body;
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "BlogPost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", body='" + body + '\'' +
                ", tags=" + tags +
                ", date=" + date +
                '}';

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /*
    {
    "_id" : ObjectId("513d396da0ee6e58987bae74"),
    "title" : "Martians to use MongoDB",
    "author" : "andrew",
    "body" : "Representatives from the planet Mars announced today that the planet would adopt MongoDB as a planetary standard. Head Martian Flipblip said that MongoDB was the perfect tool to store the diversity of life that exists on Mars.",
    "permalink" : "martians_to_use_mongodb",
    "tags" : [
        "martians",
        "seti",
        "nosql",
        "worlddomination"
    ],
    "comments" : [ ],
    "date" : ISODate("2013-03-11T01:54:53.692Z")
    }
     */

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<BlogPostComment> getComments() {
        return comments;
    }

    public void setComments(List<BlogPostComment> comments) {
        this.comments = comments;
    }

    @Id
    private ObjectId id;
    private String title;
    private String author;
    private String body;
    private List<String> tags = new ArrayList<String>();
    private List<BlogPostComment> comments = new ArrayList<BlogPostComment>();
    private Date date = new Date();

}