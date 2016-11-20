package blog.services;

import blog.model.User;
import blog.model.forms.SignUpForm;
import blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by srinivas.g on 18/11/16.
 */
@Service
public class UserDAO {
    @Autowired
    private UserRepository userRepository;
    private final ThreadLocal<Random> random = new ThreadLocal<Random>();

    public boolean addUser(SignUpForm signUpForm){

        //get the user name
        String userName = signUpForm.getUserName();
        //get the full name
        String fullName = signUpForm.getFullName();
        //get email address
        String emailAddress = signUpForm.getEmailAddress();
        //get passWord
        String passWord =  signUpForm.getPassWord();
        String passwordHash = makePasswordHash(passWord , Integer.toString(getRandom().nextInt()));

        //create a User object
        User user = new User(userName, passwordHash, fullName, emailAddress);

        userRepository.save(user);
        return true;
    }

    public User validateLogin(String userName, String password) {
        User user = userRepository.findById(userName);
        if (user == null) {
            return null;
        }

        String hashedAndSalted = user.getPasswordHash();

        String salt = hashedAndSalted.split(",")[1];

        if (!hashedAndSalted.equals(makePasswordHash(password, salt))) {
            System.out.println("Submitted password is not a match");
            return null;
        }

        return user;
    }

    private static String makePasswordHash(String password, String salt) {
        try {
            String saltedAndHashed = password + "," + salt;
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(saltedAndHashed.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            byte hashedBytes[] = (new String(digest.digest(), "UTF-8")).getBytes();
            return encoder.encode(hashedBytes) + "," + salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 is not available", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 unavailable?  Not a chance", e);
        }
    }

    private Random getRandom() {
        Random result = random.get();
        if (result == null) {
            result = new Random();
            random.set(result);
        }
        return result;
    }
}
