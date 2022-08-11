package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Atiku;
import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.dtos.requests.BlogRequest;
import africa.semicolon.soroSoke.dtos.requests.AtikuRequest;
import africa.semicolon.soroSoke.exceptions.BlogExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlogService {
    Blog saveBlog(BlogRequest newBlog) throws BlogExistsException;

    Blog saveBlog(Blog newBlog);

    Blog getBlogByTitle(String title);

    void deleteBlog(BlogRequest blogRequest);

    int getNumberOfUserBlogs();

    List<Blog> getBlog();

    Atiku addArticle(AtikuRequest newRequest);

}
