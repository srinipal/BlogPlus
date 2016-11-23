package blog.services;

import blog.model.BlogPost;
import blog.repositories.BlogPostCustomRepo;
import blog.repositories.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by srinivas.g on 21/11/16.
 */
@Service
public class BlogPostDAO {
    @Autowired
    BlogPostCustomRepo repository;

    public void createBlogPost(String title, String body, String author, String tags){
        BlogPost blogPost = new BlogPost(title, author, body, extractTags(tags));
        repository.save(blogPost);
    }

    public List<BlogPost> getPostsByAuthor(String userName){
        List<BlogPost> blogPosts = repository.findByAuthor(userName);
        if(blogPosts == null){
            blogPosts = new ArrayList<BlogPost>();
        }
        return blogPosts;
    }

    public Page<BlogPost> getLatest5Posts(){
        Page<BlogPost> latest5Posts = repository.findAll(new PageRequest(0, 5, new Sort(new Sort.Order(Sort.Direction.DESC, "date"))));
        return latest5Posts;
    }

    public Page<BlogPost> getLatest3Posts(){
        Page<BlogPost> latest3Posts = repository.findAll(new PageRequest(0, 3, new Sort(new Sort.Order(Sort.Direction.DESC, "date"))));
        return latest3Posts;
    }

    // tags the tags string and put it into an array
    private static ArrayList<String> extractTags(String tags) {

        tags = tags.replaceAll("\\s", "");
        String tagArray[] = tags.split(",");

        // let's clean it up, removing the empty string and removing dups
        ArrayList<String> tagList = new ArrayList<String>();
        for (String tag : tagArray) {
            if (!tag.equals("") && !tagList.contains(tag)) {
                tagList.add(tag);
            }
        }

        return tagList;
    }
}
