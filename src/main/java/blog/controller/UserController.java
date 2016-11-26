package blog.controller;

import blog.common.exceptions.BlogApplicationEx;
import blog.model.User;
import blog.model.forms.LoginForm;
import blog.model.forms.SignUpForm;
import blog.repositories.UserRepository;
import blog.services.SessionDAO;
import blog.services.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    private SessionDAO sessionDAO;

    @Autowired
    private HttpSession httpSession;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String signUp(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "users/register";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "users/login";
    }

    @RequestMapping(value="/addUser", method = RequestMethod.POST)
    public String addUser(@ModelAttribute SignUpForm signUpForm, BindingResult bindingResult, HttpSession httpSession){
        if(bindingResult.hasErrors()){
            //TODO: handle this condition
            //redirecting to login page
            return "users/register";
        }
        //TODO: check if this exceeds
        userDAO.addUser(signUpForm);

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
        return "redirect:/welcome";
    }

    @RequestMapping(value="/logout")
    public String logout(HttpSession httpSession){
        String sessionId = (String)httpSession.getAttribute("SessionId");
        if(sessionId == null){
            throw new BlogApplicationEx("No active session to logout", HttpStatus.BAD_REQUEST);
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
            throw new BlogApplicationEx("You must be logged in to view this page", HttpStatus.UNAUTHORIZED);
        }
        User user = userDAO.getUser(userName);
        model.addAttribute("user", user);
        return "users/profile";
    }

    @RequestMapping("/profile/{username}")
    public String viewAllProfiles(@PathVariable("username") String userName, Model model) {
        String loggedInUserName = (String)httpSession.getAttribute("UserName");
        User user = userDAO.getUser(userName);
        model.addAttribute("user", user);
        return "users/profile";
    }

}
