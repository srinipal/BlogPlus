package blog.controller;

import blog.common.exceptions.BlogApplicationEx;
import blog.model.BlogPost;
import blog.model.forms.NewPostForm;
import blog.repositories.BlogPostCustomRepo;
import blog.services.BlogPostDAO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value="/posts")
public class BlogPostController {
    @Autowired
    private BlogPostCustomRepo repository;

    @Autowired
    BlogPostDAO blogPostDAO;

    @Autowired
    HttpSession httpSession;

    @RequestMapping("/view/{id}")
    public String view(@PathVariable("id") ObjectId id, Model model) {
        BlogPost post = repository.findById(id);
        //repository.save(new BlogPost(2L, "this was inserted from app", "check if this works", null));
        model.addAttribute("post", post);
        return "posts/view";
    }

    @RequestMapping(value="/create", method = RequestMethod.GET)
    public String createPost(Model model){
        String userName = (String) httpSession.getAttribute("UserName");
        //If userName is null, indicates that user hasn't logged in
        if(userName == null){
            throw new BlogApplicationEx("Blog post can only be created by a registered user", HttpStatus.UNAUTHORIZED);
        }
        NewPostForm newPostForm = new NewPostForm();
        model.addAttribute("newPostForm", newPostForm);
        return "posts/create";
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public String createPost(NewPostForm newPostForm, BindingResult bindingResult){
        String userName = (String) httpSession.getAttribute("UserName");
        //If userName is null, indicates that user hasn't logged in
        if(userName == null){
            throw new BlogApplicationEx("Blog post can only be created by a registered user", HttpStatus.UNAUTHORIZED);
        }
        blogPostDAO.createBlogPost(newPostForm.getTitle(), newPostForm.getBody(), userName, newPostForm.getTags());
        return "redirect:/welcome";
    }

    @RequestMapping(value="/myposts")
    public String viewMyPosts(Model model){
        String userName = (String) httpSession.getAttribute("UserName");
        if(userName == null){
            throw new BlogApplicationEx("Blog posts are available only for registered user", HttpStatus.UNAUTHORIZED);
        }
        List<BlogPost> myPosts = blogPostDAO.getPostsByAuthor(userName);
        model.addAttribute("myposts", myPosts);
        return "posts/myposts";
    }

}