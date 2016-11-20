package blog.repositories;

import blog.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by srinivas.g on 18/11/16.
 */
public interface UserRepository extends MongoRepository<User, String>{
    public User save(User user);
    public User findById(String userName);
}
