package blog.repositories;

import blog.model.Session;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by srinivas.g on 20/11/16.
 */
public interface SessionRepository extends MongoRepository<Session, String> {
    public Session findById(String sessionId);
    public List<Session> deleteById(String sessionId);
}
