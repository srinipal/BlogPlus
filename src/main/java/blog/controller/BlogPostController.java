package blog.controller;

import blog.common.exceptions.BlogApplicationEx;
import blog.model.BlogPost;
import blog.model.forms.EditPostForm;
import blog.model.forms.NewPostForm;
import blog.services.BlogPostDAO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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

    /**
     * Prepare Blog Post edit form which will be used for submitting blog post update
     * @param allRequestParams
     * @param model
     * @return
     */
    @RequestMapping(value="/edit", method = RequestMethod.POST)
    public String prepareEdit(@RequestParam  Map<String, String> allRequestParams, Model model){
        String userName = (String) httpSession.getAttribute("UserName");
        //If userName is null, indicates that user hasn't logged in
        if(userName == null){
            throw new BlogApplicationEx("Blog post can only be edited by a registered user", HttpStatus.UNAUTHORIZED);
        }
        EditPostForm editPostForm = new EditPostForm();

        editPostForm.setId(allRequestParams.get("PostId"));
        editPostForm.setBody(allRequestParams.get("PostContent"));
        editPostForm.setTitle(allRequestParams.get("PostTitle"));
        editPostForm.setTags(allRequestParams.get("PostTags"));

        model.addAttribute("editPostForm", editPostForm);
        return "user_layout :: editPost";
    }


    /**
     * Save the values from blog Post to the corresponding blog entry in DB
     * @param allRequestParams
     * @param model
     * @return
     */
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public String savePost(@RequestParam Map<String, String> allRequestParams, Model model){
        String userName = (String) httpSession.getAttribute("UserName");
        //If userName is null, indicates that user hasn't logged in
        if(userName == null){
            throw new BlogApplicationEx("Blog post can only be edited by a registered user", HttpStatus.UNAUTHORIZED);
        }
        String postId = allRequestParams.get("PostId");
        String postContent = allRequestParams.get("PostContent");
        String postTitle = allRequestParams.get("PostTitle");
        String tags = allRequestParams.get("PostTags");
        BlogPost blogPost = blogPostDAO.updateBlogPost(postId,postTitle,postContent,userName, tags);
        model.addAttribute("post", blogPost);
        return "user_layout :: post";
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

    @RequestMapping(value="/addComment", method = RequestMethod.POST)
    public String addComment(@RequestParam Map<String,String> allRequestParams, Model model){
        String userName = (String) httpSession.getAttribute("UserName");
        if(userName == null){
            throw new BlogApplicationEx("Comments can be added only by a registered user", HttpStatus.UNAUTHORIZED);
        }

        String postId = allRequestParams.get("PostId");
        String commentBody = allRequestParams.get("CommentBody");
        blogPostDAO.addComment(postId, userName, commentBody);

        BlogPost blogPost = blogPostDAO.getBlogPost(new ObjectId(postId));
        model.addAttribute("post", blogPost);
        return "user_layout :: post";
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