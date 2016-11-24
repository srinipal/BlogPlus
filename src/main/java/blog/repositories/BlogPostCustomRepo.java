package blog.repositories;

import blog.model.BlogPostComment;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by srinivas.g on 19/11/16.
 */
public interface BlogPostCustomRepo {

    public void pushComment(ObjectId postId, BlogPostComment blogPostComment);

    public void upVotePost(ObjectId postId);

    public void downVotePost(ObjectId postId);
}
