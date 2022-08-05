package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.dtos.requests.AddBlogRequest;
import africa.semicolon.soroSoke.dtos.responses.BlogResponse;
import africa.semicolon.soroSoke.exceptions.BlogTitleAlreadyExists;
import org.springframework.stereotype.Service;

@Service
public interface BlogService {
    BlogResponse saveBlog(AddBlogRequest newBlogRequest) throws BlogTitleAlreadyExists;

    Blog findIfBlogTitleExists(String blogName);
}
