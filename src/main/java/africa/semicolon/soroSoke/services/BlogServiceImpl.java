package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.data.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {
    private int size = 1;
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog saveBlog(Blog blogName) {
        Blog newBlog = new Blog();
        if (size <= 1) {
            newBlog.setBlogTitle(blogName.getBlogTitle());
            blogRepository.save(newBlog);
            size++;
        }
        return newBlog;
    }

    @Override
    public boolean checkIfUserHasBlog() {
        return blogRepository.count() >= size;
    }

    @Override
    public int getNumberOfUserBlogs() {
        return (int) blogRepository.count();
    }
}
