package blog.repositories;

import blog.model.BlogPost;
import blog.model.BlogPostComment;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * Created by srinivas.g on 23/11/16.
 */
public class BlogPostRepositoryImpl implements BlogPostCustomRepo{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void pushComment(ObjectId postId, BlogPostComment blogPostComment) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("id").is(postId)),
                new Update().push("comments", blogPostComment), BlogPost.class);
    }

    @Override
    public void upVotePost(ObjectId postId) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("id").is(postId)),
                new Update().inc("likes", 1), BlogPost.class);

    }

    @Override
    public void downVotePost(ObjectId postId) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("id").is(postId)),
                new Update().inc("dislikes", 1), BlogPost.class);

    }
}
