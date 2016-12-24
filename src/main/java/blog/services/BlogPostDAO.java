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

    public BlogPost getBlogPost(ObjectId blogPostId){
        return repository.findById(blogPostId);
    }

    public BlogPost createBlogPost(String title, String body, String author, String tags){
        BlogPost blogPost = new BlogPost(title, author, body, extractTags(tags));
        return repository.save(blogPost);
    }

    public BlogPost updateBlogPost(String blogPostId, String title, String body, String author, String tags){
        ObjectId id = new ObjectId(blogPostId);
        //update the blog post first
        repository.updatePost(id, title, body, extractTags(tags), author);

        //Get the updated blog post from db
        BlogPost blogPost = repository.findById(id);
        return blogPost;
    }

    public BlogPost updateTags(String blogPostId, String author, String tags){
        ObjectId id = new ObjectId(blogPostId);
        //update the blog post first
        repository.updatePost(id, extractTags(tags), author);

        //Get the updated blog post from db
        BlogPost blogPost = repository.findById(id);
        return blogPost;
    }

    public List<BlogPost> getAllPostsByAuthor(String userName){
        List<BlogPost> blogPosts = repository.findByAuthorOrderByDateDesc(userName);
        if(blogPosts == null){
            blogPosts = new ArrayList<BlogPost>();
        }
        return blogPosts;
    }

    /**
     *
     * @param userName
     * @param pageNum
     * @return
     */
    public Page<BlogPost> getPostsByAuthor(String userName, int pageNum){
        Page<BlogPost> blogPosts = repository.findByAuthor(userName, new PageRequest(pageNum, 10, new Sort(new Sort.Order(Sort.Direction.DESC, "date"))));
        return blogPosts;
    }

    /**
     *
     * @param pageNum
     * @return
     */
    public Page<BlogPost> getLatestPosts(int pageNum){
        Page<BlogPost> latestPosts = repository.findAll(new PageRequest(pageNum, 10, new Sort(new Sort.Order(Sort.Direction.DESC, "date"))));
        return latestPosts;
    }

    /**
     *
     * @return
     */
    public Page<BlogPost> getPopularPosts(int pageNum){
        Page<BlogPost> popularPosts = repository.findAll(new PageRequest(pageNum, 10, new Sort(new Sort.Order(Sort.Direction.DESC, "likes"), new Sort.Order(Sort.Direction.ASC, "dislikes"), new Sort.Order(Sort.Direction.DESC, "date"))));
        return popularPosts;
    }

    public Page<BlogPost> getPostsWithTag(String tag, int pageNum){
        Page<BlogPost> blogPosts = repository.findByTags(tag, new PageRequest(pageNum, 10, new Sort(new Sort.Order(Sort.Direction.DESC, "date"))));
        return blogPosts;
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

    public BlogPost upVotePost(String postId){
        ObjectId id = new ObjectId(postId);
        repository.upVotePost(id);
        return repository.findById(id);
    }

    public BlogPost downVotePost(String postId){
        ObjectId id = new ObjectId(postId);
        repository.downVotePost(id);
        return repository.findById(id);
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
