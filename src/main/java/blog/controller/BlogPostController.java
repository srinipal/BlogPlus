package blog.controller;

import blog.common.exceptions.BlogApplicationEx;
import blog.model.BlogPost;
import blog.model.forms.AddCommentForm;
import blog.model.forms.NewPostForm;
import blog.repositories.BlogPostCustomRepo;
import blog.repositories.BlogPostRepository;
import blog.services.BlogPostDAO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Binding;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by srinivas.g on 18/11/16.
 */
@Controller
@RequestMapping(value="/posts")
public class BlogPostController {

    @Autowired
    BlogPostDAO blogPostDAO;

    @Autowired
    HttpSession httpSession;

    @RequestMapping("/view/{id}")
    public String view(@PathVariable("id") ObjectId id, Model model) {
        BlogPost post = blogPostDAO.getBlogPost(id);
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

    @RequestMapping(value="/add_comment", method = RequestMethod.POST)
    public ModelAndView addComment(@RequestParam Map<String,String> allRequestParams){
        String userName = (String) httpSession.getAttribute("UserName");
        if(userName == null){
            throw new BlogApplicationEx("Comments can be added only by a registered user", HttpStatus.UNAUTHORIZED);
        }

        String postId = allRequestParams.get("comment_post_id");
        String commentBody = allRequestParams.get("comment_body");
        blogPostDAO.addComment(postId, userName, commentBody);
        return new ModelAndView("redirect:/posts/view/" + postId);
    }




    @RequestMapping(value="/upvote", method = RequestMethod.POST)
    public String upVote(@RequestParam Map<String, String> allRequestParams, Model model){
        String postId = allRequestParams.get("PostId");
        BlogPost blogPost = blogPostDAO.upVotePost(postId);
        model.addAttribute("post", blogPost);
        return "user_layout :: post";
    }


    @RequestMapping(value="/downvote", method = RequestMethod.POST)
    public String downVote(@RequestParam Map<String, String> allRequestParams, Model model){
        String postId = allRequestParams.get("PostId");
        BlogPost blogPost = blogPostDAO.downVotePost(postId);
        model.addAttribute("post", blogPost);
        return "user_layout :: post";
    }
}