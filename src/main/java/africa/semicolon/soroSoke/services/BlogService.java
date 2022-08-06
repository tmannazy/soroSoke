package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.exceptions.BlogExistsException;
import org.springframework.stereotype.Service;

@Service
public interface BlogService {
    Blog saveBlog(Blog newBlog) throws BlogExistsException;

    boolean checkIfUserHasBlog();

    int getNumberOfUserBlogs();
}
