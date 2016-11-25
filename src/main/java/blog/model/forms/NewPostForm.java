package blog.model.forms;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Created by srinivas.g on 21/11/16.
 */
public class NewPostForm {
    private String title;
    private String body;
    private String tags;

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
        //html escaping the input here
        this.body = body;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
