package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.data.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService{
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog saveBlog(Blog blogName){
        Blog newBlog = new Blog();
        newBlog.setBlogTitle(blogName.getBlogTitle());
        blogRepository.save(newBlog);
        return newBlog;
    }

    @Override
    public Blog findIfBlogTitleExists(String blogName) {
        return blogRepository.findBlogByBlogTitleContainingIgnoreCase(blogName);
    }

    @Override
    public int getNumberOfUserBlogs() {
        return (int) blogRepository.count();
    }
}
