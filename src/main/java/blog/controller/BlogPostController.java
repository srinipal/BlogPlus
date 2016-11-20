package blog.controller;

import blog.model.BlogPost;
import blog.repositories.BlogPostCustomRepo;
import blog.repositories.BlogPostRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by srinivas.g on 18/11/16.
 */
@Controller
@RequestMapping("/posts")
public class BlogPostController {
    @Autowired
    private BlogPostCustomRepo repository;

    @RequestMapping("/view/{id}")
    public String view(@PathVariable("id") ObjectId id, Model model) {
        BlogPost post = repository.findById(id);
        //repository.save(new BlogPost(2L, "this was inserted from app", "check if this works", null));
        model.addAttribute("post", post);
        return "posts/view";
    }
}