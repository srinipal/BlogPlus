package blog.model;

/**
 * Created by srinivas.g on 18/11/16.
 */

import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

public class User {

    @Id
    private String id;
    private String passwordHash;
    private String fullName;
    private String emailAddress;


    public User(){

    }

    public User(String id, String passwordHash, String fullName, String emailAddress) {
        this.id = id;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}