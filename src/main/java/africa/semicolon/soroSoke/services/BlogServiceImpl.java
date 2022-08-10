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
    public Blog saveBlog(AddBlogRequest blogName) throws BlogExistsException {
        Blog newBlog = new Blog();
        if (blogRepository.count() < 1) {
            newBlog.setBlogTitle(blogName.getBlogTitle());
            blogRepository.save(newBlog);
            return newBlog;
        }
        else {
            throw new BlogExistsException("User " + blogName.getUserName().toUpperCase() + " already has a blog.");
        }
    }

    @Override
    public Blog saveBlog(Blog newBlog) {
        return blogRepository.save(newBlog);
    }

    @Override
    public Blog getBlogByTitle(String title) {
        var foundBlog = blogRepository.getBlogByBlogTitleIgnoreCase(title);
        return foundBlog;
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
