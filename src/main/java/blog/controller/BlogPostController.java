package blog.controller;

import blog.common.exceptions.BadRequestException;
import blog.common.exceptions.RestrictedAccessException;
import blog.model.BlogPost;
import blog.model.forms.NewPostForm;
import blog.services.BlogPostDAO;
import org.apache.commons.lang.StringEscapeUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by srinivas.g on 18/11/16.
 */
@Controller
@RequestMapping(value="/blogplus/posts")
public class BlogPostController {

    @Autowired
    BlogPostDAO blogPostDAO;

    @Autowired
    HttpSession httpSession;

    @RequestMapping("/view/{id}")
    public String view(@PathVariable("id") String id, Model model) {
        ObjectId postId = null;
        try{
            postId = new ObjectId(id);
        }
        catch(IllegalArgumentException ex){
            ;//Indicates that id value is not i expected format
            throw new BadRequestException("The requested blog post doesn't exist", HttpStatus.NOT_FOUND);
        }
        BlogPost post = blogPostDAO.getBlogPost(postId);
        if(post == null){
            throw new BadRequestException("The requested blog post doesn't exist", HttpStatus.NOT_FOUND);
        }
        model.addAttribute("post", post);
        return "posts/view";
    }

    @RequestMapping(value="/create", method = RequestMethod.GET)
    public String createPost(Model model){
        String userName = (String) httpSession.getAttribute("UserName");
        //If userName is null, indicates that user hasn't logged in
        if(userName == null){
            throw new RestrictedAccessException("A blog post can only be created by a registered user", HttpStatus.UNAUTHORIZED);
        }
        NewPostForm newPostForm = new NewPostForm();
        model.addAttribute("newPostForm", newPostForm);
        return "posts/create";
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public String createPost(NewPostForm newPostForm, BindingResult bindingResult, Model model){
        String userName = (String) httpSession.getAttribute("UserName");
        //If userName is null, indicates that user hasn't logged in
        if(userName == null){
            throw new RestrictedAccessException("A blog post can only be created by a registered user", HttpStatus.UNAUTHORIZED);
        }

        //escaping html before saving to database
        String title = StringEscapeUtils.escapeHtml(newPostForm.getTitle());
        String body = StringEscapeUtils.escapeHtml(newPostForm.getBody());
        String tags = StringEscapeUtils.escapeHtml(newPostForm.getTags());

        BlogPost post = blogPostDAO.createBlogPost(title, body, userName, tags);
        model.addAttribute("post", post);
        return "posts/view";
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
            throw new RestrictedAccessException("A blog post can only be edited by a registered user", HttpStatus.UNAUTHORIZED);
        }

        //escaping html before saving to database
        String postId = allRequestParams.get("PostId");
        String postContent = StringEscapeUtils.escapeHtml(allRequestParams.get("PostContent"));
        String postTitle = StringEscapeUtils.escapeHtml(allRequestParams.get("PostTitle"));
        String tags = StringEscapeUtils.escapeHtml(allRequestParams.get("PostTags"));


        BlogPost blogPost = blogPostDAO.updateBlogPost(postId, postTitle, postContent, userName, tags);
        model.addAttribute("post", blogPost);
        return "user_layout :: post";
    }

    @RequestMapping(value="/addComment", method = RequestMethod.POST)
    public String addComment(@RequestParam Map<String,String> allRequestParams, Model model){
        String userName = (String) httpSession.getAttribute("UserName");
        if(userName == null){
            throw new BadRequestException("Comments can only be added by a registered user", HttpStatus.UNAUTHORIZED);
        }

        String postId = allRequestParams.get("PostId");
        //escaping html before saving to database
        String commentBody = StringEscapeUtils.escapeHtml(allRequestParams.get("CommentBody"));
        blogPostDAO.addComment(postId, userName, commentBody);

        BlogPost blogPost = blogPostDAO.getBlogPost(new ObjectId(postId));
        model.addAttribute("post", blogPost);
        return "user_layout :: post";
    }

    @RequestMapping(value="/updateTags", method = RequestMethod.POST)
    public String updateTags(@RequestParam Map<String,String> allRequestParams, Model model){
        String userName = (String) httpSession.getAttribute("UserName");
        //If userName is null, indicates that user hasn't logged in
        if(userName == null){
            throw new RestrictedAccessException("A blog post can only be edited by a registered user", HttpStatus.UNAUTHORIZED);
        }

        //escaping html before saving to database
        String postId = allRequestParams.get("PostId");
        String tags = StringEscapeUtils.escapeHtml(allRequestParams.get("PostTags"));


        BlogPost blogPost = blogPostDAO.updateTags(postId, userName, tags);
        model.addAttribute("post", blogPost);
        return "user_layout :: post";
    }

    @RequestMapping(value="/upvote", method = RequestMethod.POST)
    public String upVote(@RequestParam Map<String, String> allRequestParams, Model model){
        String userName = (String) httpSession.getAttribute("UserName");
        if(userName == null){
            throw new BadRequestException("You must be logged in to perform this operation", HttpStatus.UNAUTHORIZED);
        }
        String postId = allRequestParams.get("PostId");
        BlogPost blogPost = blogPostDAO.upVotePost(postId);
        model.addAttribute("post", blogPost);
        return "user_layout :: post";
    }


    @RequestMapping(value="/downvote", method = RequestMethod.POST)
    public String downVote(@RequestParam Map<String, String> allRequestParams, Model model){
        String userName = (String) httpSession.getAttribute("UserName");
        if(userName == null){
            throw new BadRequestException("You must be logged in to perform this operation", HttpStatus.UNAUTHORIZED);
        }
        String postId = allRequestParams.get("PostId");
        BlogPost blogPost = blogPostDAO.downVotePost(postId);
        model.addAttribute("post", blogPost);
        return "user_layout :: post";
    }

    @RequestMapping("/filter")
    public String filterBlogPosts(@RequestParam Map<String, String> allRequestParams, Model model) {

        //If no filter parameters are passed, then throw an error
        if(allRequestParams.isEmpty()){
            throw new BadRequestException("Atleast one filter parameter has to be passed", HttpStatus.BAD_REQUEST);
        }

        String tag = allRequestParams.get("tag");

        Page<BlogPost> posts = null;

        if(tag.isEmpty()){//If tag is not passed or it is empty
            posts = blogPostDAO.getLatestPosts(0);
        }
        else{
            //trim tag
            tag = tag.trim();
            posts = blogPostDAO.getPostsWithTag(tag, 0);
        }

        model.addAttribute("posts", posts);

        //Store tag also in the model
        model.addAttribute("tag", tag);

        return "posts/filteredPosts";
    }

    @RequestMapping("/filter/{nextPage}")
    public String filterBlogPostsMore(@PathVariable("nextPage") int nextPage, @RequestParam Map<String, String> allRequestParams, Model model) {

        //If no filter parameters are passed, then throw an error
        if(allRequestParams.isEmpty()){
            throw new BadRequestException("Atleast one filter parameter has to be passed", HttpStatus.BAD_REQUEST);
        }

        String tag = allRequestParams.get("tag");

        Page<BlogPost> posts = null;

        if(tag.isEmpty()){//If tag is not passed or it is empty
            posts = blogPostDAO.getLatestPosts(nextPage);
        }
        else{
            //trim tag
            tag = tag.trim();
            posts = blogPostDAO.getPostsWithTag(tag, nextPage);
        }

        if(posts == null || !posts.hasContent()){
            return "user_layout :: noMoreEntries";
        }

        model.addAttribute("posts", posts);

        //Store tag also in the model
        model.addAttribute("tag", tag);

        return "user_layout :: postList";
    }

}