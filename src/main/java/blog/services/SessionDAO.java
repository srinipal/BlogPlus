package blog.services;

import blog.model.Session;
import blog.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.security.SecureRandom;

/**
 * Created by srinivas.g on 20/11/16.
 */
@Service
public class SessionDAO {
    @Autowired
    private SessionRepository sessionRepository;

    public String findUserNameBySessionId(String sessionId) {
        Session session = sessionRepository.findById(sessionId);

        if (session == null) {
            return null;
        }
        else {
            return session.getUserName();
        }
    }


    // starts a new session in the sessions table
    public String startSession(String userName) {

        // get 32 byte random number. that's a lot of bits.
        SecureRandom generator = new SecureRandom();
        byte randomBytes[] = new byte[32];
        generator.nextBytes(randomBytes);

        BASE64Encoder encoder = new BASE64Encoder();

        String sessionId = encoder.encode(randomBytes);

        // build the Session object
        Session session = new Session(sessionId, userName);
        //add the session
        sessionRepository.save(session);
        return sessionId;
    }

    // ends the session by deleting it from the sesisons table
    public void endSession(String sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
