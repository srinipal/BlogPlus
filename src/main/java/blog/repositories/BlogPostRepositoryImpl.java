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

import java.util.List;

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

    @Override
    public void updatePost(ObjectId postId, String postTitle, String postContent, List<String> tags, String author) {

        //query construction
        Query searchQuery = new Query();
        searchQuery.addCriteria(Criteria.where("id").is(postId));
        searchQuery.addCriteria(Criteria.where("author").is(author));


        searchQuery.fields().include("title");
        searchQuery.fields().include("body");
        searchQuery.fields().include("tags");

        //Update construction
        Update update = new Update();
        update.set("title", postTitle);
        update.set("body", postContent);
        update.set("tags", tags);

        mongoTemplate.updateFirst(searchQuery, update, BlogPost.class);
    }

    @Override
    public void updatePost(ObjectId postId, List<String> tags, String author) {
        //query construction
        Query searchQuery = new Query();
        searchQuery.addCriteria(Criteria.where("id").is(postId));
        searchQuery.addCriteria(Criteria.where("author").is(author));


        searchQuery.fields().include("title");
        searchQuery.fields().include("body");
        searchQuery.fields().include("tags");

        //Update construction
        Update update = new Update();
        update.set("tags", tags);

        mongoTemplate.updateFirst(searchQuery, update, BlogPost.class);
    }
}
