package blog.controller;

import blog.model.BlogPost;
import blog.repositories.BlogPostCustomRepo;
import blog.repositories.BlogPostRepository;
import blog.services.BlogPostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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
        Page<BlogPost> latest5Posts = blogPostDAO.getLatest5Posts();
        model.addAttribute("latest5posts", latest5Posts);

        Page<BlogPost> latest3Posts = blogPostDAO.getLatest3Posts();
        model.addAttribute("latest3posts", latest3Posts);
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome(Model model) {
        //TODO: get popular posts instead of this
        Page<BlogPost> latest5Posts = blogPostDAO.getLatest5Posts();
        model.addAttribute("latest5posts", latest5Posts);

        Page<BlogPost> latest3Posts = blogPostDAO.getLatest3Posts();
        model.addAttribute("latest3posts", latest3Posts);
        return "welcome";
    }


}
