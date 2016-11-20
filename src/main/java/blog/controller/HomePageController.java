package blog.controller;

import blog.model.BlogPost;
import blog.repositories.BlogPostCustomRepo;
import blog.repositories.BlogPostRepository;
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
    private BlogPostCustomRepo repository;

    @RequestMapping("/")
    public String index(Model model) {
        Page<BlogPost> latest5Posts = repository.findAll(new PageRequest(0, 5, new Sort(new Sort.Order(Sort.Direction.DESC, "date"))));
        model.addAttribute("latest5posts", latest5Posts);
        List<BlogPost> latest3Posts = new ArrayList<BlogPost>();

        int i = 0;
        for(BlogPost blogPost : latest5Posts){
            if(++i <= 3){
                latest3Posts.add(blogPost);
            }
        }
        model.addAttribute("latest3posts", latest3Posts);
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome(Model model) {
        Page<BlogPost> latest5Posts = repository.findAll(new PageRequest(0, 5, new Sort(new Sort.Order(Sort.Direction.DESC, "date"))));
        model.addAttribute("latest5posts", latest5Posts);
        List<BlogPost> latest3Posts = new ArrayList<BlogPost>();

        int i = 0;
        for(BlogPost blogPost : latest5Posts){
            if(++i <= 3){
                latest3Posts.add(blogPost);
            }
        }
        model.addAttribute("latest3posts", latest3Posts);
        return "welcome";
    }


}
