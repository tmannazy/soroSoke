package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.data.repositories.BlogRepository;
import africa.semicolon.soroSoke.dtos.requests.AddBlogRequest;
import africa.semicolon.soroSoke.dtos.responses.BlogResponse;
import africa.semicolon.soroSoke.exceptions.BlogTitleAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService{
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public BlogResponse saveBlog(AddBlogRequest blogRequest) throws BlogTitleAlreadyExists {
        Blog newBlog = new Blog();
        var validateBlogName = blogRepository.findBlogByBlogTitleContainingIgnoreCase(blogRequest.getBlogTitle());
        if (validateBlogName != null) {
            throw new BlogTitleAlreadyExists(blogRequest.getBlogTitle() + " already exists.");
        }
        newBlog.setBlogTitle(blogRequest.getBlogTitle());
        blogRepository.save(newBlog);
        BlogResponse response = new BlogResponse();
        response.setMessage("User with " + blogRequest.getBlogTitle() + " successfully registered.");
        return response;
    }

    @Override
    public Blog findIfBlogTitleExists(String blogName) {
        return blogRepository.findBlogByBlogTitleContainingIgnoreCase(blogName);
    }
}
