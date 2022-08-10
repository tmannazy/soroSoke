package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Atiku;
import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.dtos.requests.AddBlogRequest;
import africa.semicolon.soroSoke.dtos.requests.AtikuRequest;
import africa.semicolon.soroSoke.exceptions.BlogExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlogService {
    Blog saveBlog(AddBlogRequest newBlog) throws BlogExistsException;

    Blog saveBlog(Blog newBlog);

    Blog getBlogByTitle(String title);

    void deleteBlog(Blog blog);

    int getNumberOfUserBlogs();

    List<Blog> getBlog();

    Atiku addArticle(AtikuRequest newRequest);

}
