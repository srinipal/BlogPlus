package blog.controller;

import blog.model.BlogPost;
import blog.services.BlogPostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

/**
 * Created by srinivas.g on 17/11/16.
 */
@Controller
public class HomePageController {

    @Autowired
    private BlogPostDAO blogPostDAO;

    /**
     * Default page for a guest user
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String index(Model model) {
        //Get the latest posts
        Page<BlogPost> latestPosts = blogPostDAO.getLatestPosts();
        model.addAttribute("posts", latestPosts);

        //Get the popular posts
        Page<BlogPost> popularPosts = blogPostDAO.getPopularPosts();
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
        Page<BlogPost> latestPosts = blogPostDAO.getLatestPosts();
        model.addAttribute("posts", latestPosts);

        //Get the popular posts
        Page<BlogPost> popularPosts = blogPostDAO.getPopularPosts();
        model.addAttribute("popularPosts", popularPosts);
        return "welcome";
    }


}
