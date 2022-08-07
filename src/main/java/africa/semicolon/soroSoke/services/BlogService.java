package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.dtos.requests.AddBlogRequest;
import africa.semicolon.soroSoke.exceptions.BlogExistsException;
import org.springframework.stereotype.Service;

@Service
public interface BlogService {
    Blog saveBlog(AddBlogRequest newBlog) throws BlogExistsException;

    Blog getBlogByTitle(String title);

    void deleteBlog(Blog blog);


    int getNumberOfUserBlogs();
}
