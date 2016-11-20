package blog.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * Created by srinivas.g on 20/11/16.
 */
public class Session {
    public Session(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    @Id
    private String id;
    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
