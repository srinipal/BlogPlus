package blog.repositories;

import blog.model.BlogPost;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by srinivas.g on 18/11/16.
 */
public interface BlogPostRepository extends MongoRepository<BlogPost, ObjectId> {

    public BlogPost findById(ObjectId id);
    public Page<BlogPost> findAll(Pageable pageable);

    //public BlogPost findOne();
}
