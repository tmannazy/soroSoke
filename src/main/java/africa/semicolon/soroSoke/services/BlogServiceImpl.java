package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Atiku;
import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.data.repositories.BlogRepository;
import africa.semicolon.soroSoke.dtos.requests.AddBlogRequest;
import africa.semicolon.soroSoke.dtos.requests.AtikuRequest;
import africa.semicolon.soroSoke.exceptions.BlogExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AtikuService atikuService;

    @Override
    public Blog saveBlog(AddBlogRequest blogName) {
        Blog newBlog = new Blog();
        var searchBlog = blogRepository.getBlogByBlogTitleIgnoreCase(blogName.getBlogTitle());
        if(searchBlog == null) {
            newBlog.setBlogTitle(blogName.getBlogTitle());
            blogRepository.save(newBlog);
            return newBlog;
        } else{
            searchBlog.setBlogTitle(blogName.getBlogTitle());
            return searchBlog;
        }
    }

    @Override
    public Blog saveBlog(Blog newBlog) {
        return blogRepository.save(newBlog);
    }

    @Override
    public Blog getBlogByTitle(String title) {
        return blogRepository.getBlogByBlogTitleIgnoreCase(title);
    }

    @Override
    public void deleteBlog(Blog blog) {
        blogRepository.delete(blog);
    }

    @Override
    public int getNumberOfUserBlogs() {
        return (int) blogRepository.count();
    }

    @Override
    public List<Blog> getBlog() {
        return blogRepository.findAll();
    }

    @Override
    public Atiku addArticle(AtikuRequest request) {
        Atiku newAtiku = new Atiku();
        newAtiku.setTitle(request.getTitle());
        newAtiku.setTime(request.getTime());
        newAtiku.setBody(request.getBody());
        return atikuService.saveArticle(newAtiku);
    }

}
