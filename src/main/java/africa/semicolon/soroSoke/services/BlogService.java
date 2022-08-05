package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.exceptions.BlogTitleExists;
import org.springframework.stereotype.Service;

@Service
public interface BlogService {
    Blog saveBlog(Blog newBlog) throws BlogTitleExists;

    Blog findIfBlogTitleExists(String blogName);

    int getNumberOfUserBlogs();
}
