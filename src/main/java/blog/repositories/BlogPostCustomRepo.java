package blog.repositories;

import blog.model.BlogPostComment;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

/**
 * Custom repository to provide additional methods to achieve CRUD operations that are not
 * already defined in MongoRepository
 * Created by srinivas.g on 19/11/16.
 */
public interface BlogPostCustomRepo {

    /**
     * Add a comment to an existing blog
     * @param postId
     * @param blogPostComment
     */
    public void pushComment(ObjectId postId, BlogPostComment blogPostComment);

    /**
     * Upvote a blog post
     * @param postId
     */
    public void upVotePost(ObjectId postId);

    /**
     * Downvote a blog post
     * @param postId
     */
    public void downVotePost(ObjectId postId);

    public void updatePost(ObjectId postId, String postTitle, String postContent, List<String> tags, String author);
}
