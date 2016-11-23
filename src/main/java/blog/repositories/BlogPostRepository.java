package blog.repositories;

import blog.model.BlogPost;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * Created by srinivas.g on 18/11/16.
 */
public interface BlogPostRepository extends MongoRepository<BlogPost, ObjectId>, BlogPostCustomRepo{

    public BlogPost findById(ObjectId id);
    public Page<BlogPost> findAll(Pageable pageable);

    public List<BlogPost> findByAuthorOrderByDateDesc(String userName);
}
