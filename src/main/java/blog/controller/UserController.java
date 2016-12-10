package blog.controller;

import blog.common.exceptions.BadRequestException;
import blog.common.exceptions.RestrictedAccessException;
import blog.model.BlogPost;
import blog.model.User;
import blog.model.forms.LoginForm;
import blog.model.forms.SignUpForm;
import blog.repositories.UserRepository;
import blog.services.BlogPostDAO;
import blog.services.SessionDAO;
import blog.services.UserDAO;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by srinivas.g on 18/11/16.
 */
@Controller
@RequestMapping("/users")
public class UserController {
    //private final UserDAO userDAO = new UserDAO();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BlogPostDAO blogPostDAO;

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    private HttpSession httpSession;

    /**
     * Add a signup form to the model and return the register page
     * @param model
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String signUp(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "users/register";
    }

    /**
     * Add a a login form to the model and return the login page
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "users/login";
    }

    /**
     *
     * @param signUpForm
     * @param bindingResult
     * @param httpSession
     * @return
     */
    @RequestMapping(value="/addUser", method = RequestMethod.POST)
    public String addUser(@ModelAttribute SignUpForm signUpForm, BindingResult bindingResult, HttpSession httpSession){
        if(bindingResult.hasErrors()){
            //TODO: handle this condition
            //redirecting to login page
            return "users/register";
        }
        //Add user
        boolean userAdded = userDAO.addUser(signUpForm);

        //get the username from newly created user object
        String userName = signUpForm.getUserName();
        //start session
        String sessionId = sessionDAO.startSession(userName);

        //Add UserName as session attribute
        httpSession.setAttribute("UserName", userName);
        //Add SessionId as session attribute
        httpSession.setAttribute("SessionId", sessionId);
        return "redirect:/welcome";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String login(@ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpSession httpSession){
        if(bindingResult.hasErrors()){
            //TODO: handle this condition
            //redirecting to login page
            return "users/register";
        }
        User user = userDAO.validateLogin(loginForm.getUserName(), loginForm.getPassword());

        //begin the session if user was created successfully
        if(user != null){
            //get the username from newly created user object
            String userName = user.getId();
            String sessionId = sessionDAO.startSession(userName);
            //Add UserName as session attribute
            httpSession.setAttribute("UserName", userName);
            //Add SessionId as session attribute
            httpSession.setAttribute("SessionId", sessionId);
        }
        if(user == null){
            throw new BadRequestException("The username or password is incorrect", HttpStatus.UNAUTHORIZED);
        }

        return "redirect:/welcome";
    }

    @RequestMapping(value="/logout")
    public String logout(HttpSession httpSession){
        String sessionId = (String)httpSession.getAttribute("SessionId");
        if(sessionId == null){
            throw new BadRequestException("No active session to logout", HttpStatus.BAD_REQUEST);
        }
        //TODO: end the session here
        sessionDAO.endSession(sessionId);
        httpSession.invalidate();
        return "redirect:/";
    }

    @RequestMapping(value="/profile")
    public String viewMyProfile(Model model, HttpSession httpSession){
        String userName = (String)httpSession.getAttribute("UserName");
        if(userName == null){
            throw new RestrictedAccessException("You must be logged in to access this page", HttpStatus.UNAUTHORIZED);
        }
        User user = userDAO.getUser(userName);
        if(user == null){
            throw new BadRequestException("The user profile for " + userName + " doesn't exist", HttpStatus.NOT_FOUND);
        }
        model.addAttribute("user", user);

        List<BlogPost> blogPosts = blogPostDAO.getPostsByAuthor(userName);
        model.addAttribute("posts", blogPosts);
        return "users/profile";
    }

    @RequestMapping("/profile/{username}")
    public String viewAllProfiles(@PathVariable("username") String userName, Model model) {
        String loggedInUserName = (String)httpSession.getAttribute("UserName");
        User user = userDAO.getUser(userName);
        if(user == null){
            throw new BadRequestException("The user profile for " + userName + " doesn't exist", HttpStatus.NOT_FOUND);
        }
        model.addAttribute("user", user);
        Page<BlogPost> blogPosts = blogPostDAO.getPostsByAuthor(userName, 0);
        model.addAttribute("posts", blogPosts);

        return "users/profile";
    }


    @RequestMapping("/profile/{username}/posts/{nextPage}")
    public String getMorePosts(@PathVariable("username") String userName, @PathVariable("nextPage") int nextPage, Model model) {
        String loggedInUserName = (String)httpSession.getAttribute("UserName");
        User user = userDAO.getUser(userName);
        if(user == null){
            throw new BadRequestException("The user profile for " + userName + " doesn't exist", HttpStatus.NOT_FOUND);
        }

        Page<BlogPost> blogPosts = blogPostDAO.getPostsByAuthor(userName, nextPage);
        if(!blogPosts.hasContent()){
            return "user_layout :: noMoreEntries";
        }
        model.addAttribute("posts", blogPosts);
        return "user_layout :: postList";
    }


}
