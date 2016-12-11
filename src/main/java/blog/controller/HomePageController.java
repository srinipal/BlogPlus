package blog.controller;

import blog.common.exceptions.BadRequestException;
import blog.model.BlogPost;
import blog.services.BlogPostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by srinivas.g on 17/11/16.
 */
@Controller
@RequestMapping(value="/blogplus")
public class HomePageController {

    @Autowired
    private BlogPostDAO blogPostDAO;

    /**
     * Default page for a guest user
     * @param model
     * @return
     */
    @RequestMapping(value="")
    public String index(Model model) {
        //Get the latest posts
        Page<BlogPost> latestPosts = blogPostDAO.getLatestPosts(0);
        model.addAttribute("posts", latestPosts);

        //Get the popular posts
        Page<BlogPost> popularPosts = blogPostDAO.getPopularPosts(0);
        model.addAttribute("popularPosts", popularPosts);
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome(@SessionAttribute(value = "SessionId", required = false)String sessionId, Model model) {
        //if SessionId is null redirect to index poage
        if(sessionId == null){
            return "redirect:/";
        }
        //Get the latest posts
        Page<BlogPost> latestPosts = blogPostDAO.getLatestPosts(0);
        model.addAttribute("posts", latestPosts);

        //Get the popular posts
        Page<BlogPost> popularPosts = blogPostDAO.getPopularPosts(0);
        model.addAttribute("popularPosts", popularPosts);
        return "welcome";
    }

    @RequestMapping(value = "/morePosts/{nextPage}", method = RequestMethod.GET)
    public String getMorePosts(@PathVariable int nextPage, Model model){

        Page<BlogPost> nextPosts = blogPostDAO.getLatestPosts(nextPage);
        if(!nextPosts.hasContent()){
            return "user_layout :: noMoreEntries";
        }
        model.addAttribute("posts", nextPosts);
        return "user_layout :: postList";
    }
}
