package blog.model.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by srinivas.g on 18/11/16.
 */
public class SignUpForm {

    private String userName;

    private String fullName;

    private String passWord;

    private String emailAddress;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
