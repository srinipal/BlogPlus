package blog.model.forms;

import org.bson.types.ObjectId;

/**
 * Created by srinivas.g on 23/11/16.
 */
public class AddCommentForm {
    private String commentBody;
    private ObjectId postId;

    public ObjectId getPostId() {
        return postId;
    }

    public void setPostId(ObjectId postId) {
        this.postId = postId;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }
}
