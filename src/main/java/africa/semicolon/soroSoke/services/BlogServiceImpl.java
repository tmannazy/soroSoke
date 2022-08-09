package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.data.repositories.BlogRepository;
import africa.semicolon.soroSoke.dtos.requests.AddBlogRequest;
import africa.semicolon.soroSoke.exceptions.BlogExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

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
}
