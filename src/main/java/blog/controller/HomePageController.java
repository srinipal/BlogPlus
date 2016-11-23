package blog.controller;

import blog.model.BlogPost;
import blog.services.BlogPostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by srinivas.g on 17/11/16.
 */
@Controller
public class HomePageController {

    @Autowired
    private BlogPostDAO blogPostDAO;

    @RequestMapping("/")
    public String index(Model model) {
        //TODO: get popular posts instead of this
        Page<BlogPost> latestPosts = blogPostDAO.getLatestPosts();
        model.addAttribute("latestPosts", latestPosts);

        //Calculate the popular posts
        List<BlogPost> popularPosts = blogPostDAO.sortByPopularity(latestPosts);
        model.addAttribute("popularPosts", popularPosts);
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome(Model model) {
        //TODO: get popular posts instead of this
        Page<BlogPost> latestPosts = blogPostDAO.getLatestPosts();
        model.addAttribute("latestPosts", latestPosts);

        //Calculate the popular posts
        List<BlogPost> popularPosts = blogPostDAO.sortByPopularity(latestPosts);
        model.addAttribute("popularPosts", popularPosts);
        return "welcome";
    }


}
