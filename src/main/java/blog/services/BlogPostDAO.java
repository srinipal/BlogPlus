package blog.services;

import blog.model.BlogPost;
import blog.model.BlogPostComment;
import blog.repositories.BlogPostCustomRepo;
import blog.repositories.BlogPostRepository;
import com.fasterxml.jackson.databind.ser.std.CollectionSerializer;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by srinivas.g on 21/11/16.
 */
@Service
public class BlogPostDAO {
    @Autowired
    BlogPostRepository repository;

    public void createBlogPost(String title, String body, String author, String tags){
        BlogPost blogPost = new BlogPost(title, author, body, extractTags(tags));
        repository.save(blogPost);
    }

    public List<BlogPost> getPostsByAuthor(String userName){
        List<BlogPost> blogPosts = repository.findByAuthorOrderByDateDesc(userName);
        if(blogPosts == null){
            blogPosts = new ArrayList<BlogPost>();
        }
        return blogPosts;
    }

    public Page<BlogPost> getLatestPosts(){
        Page<BlogPost> latestPosts = repository.findAll(new PageRequest(0, 10, new Sort(new Sort.Order(Sort.Direction.DESC, "date"))));
        return latestPosts;
    }

    public Page<BlogPost> getPopularPosts(){
        //TODO: implement this later
        return null;
    }


    // tags the tags string and put it into an array
    private static ArrayList<String> extractTags(String tags) {

        tags = tags.replaceAll("\\s", "");
        String tagArray[] = tags.split(",");

        // let's clean it up, removing the empty string and removing dups
        ArrayList<String> tagList = new ArrayList<String>();
        for (String tag : tagArray) {
            if (!tag.equals("") && !tagList.contains(tag)) {
                tagList.add(tag.toLowerCase());
            }
        }

        return tagList;
    }



    public void addComment(String postId, String author, String commentBody){
        //Convert String id to ObjectId
        ObjectId id = new ObjectId(postId);
        repository.pushComment(id, new BlogPostComment(commentBody, author));
    }
    
    public static List<BlogPost> sortByPopularity(Page<BlogPost> blogPosts){
        //List to hold the popular posts

        //TODO: this logic should be database based, move this from in memory
        List<BlogPost> sortedBlogPosts = new ArrayList<BlogPost>();
        for(BlogPost blogPost : blogPosts){
            sortedBlogPosts.add(blogPost);
        }
        Collections.sort(sortedBlogPosts, new Comparator<BlogPost>() {
            public int compare(BlogPost a, BlogPost b){
                Integer numCommentsA = a.getComments().size();
                Integer numCommentsB = b.getComments().size();

                if(numCommentsA == numCommentsB){
                    //If number of comments are same, return the latest one
                    return b.getDate().compareTo(a.getDate());
                }
                else{
                    return numCommentsB.compareTo(numCommentsA);

                }
            }
        });
        return sortedBlogPosts;
    }
}
