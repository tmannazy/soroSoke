package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Atiku;
import africa.semicolon.soroSoke.dtos.requests.*;
import africa.semicolon.soroSoke.dtos.responses.*;
import africa.semicolon.soroSoke.exceptions.ArticleRequestException;
import africa.semicolon.soroSoke.exceptions.BlogExistsException;
import africa.semicolon.soroSoke.exceptions.InvalidUserNameOrPasswordException;
import africa.semicolon.soroSoke.exceptions.UserExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    RegisterUserResponse registerUser(RegisterRequest registerRequest) throws UserExistsException;

    BlogResponse createNewBlog(BlogRequest createBlog) throws BlogExistsException;

    LoginResponse userLogin(LoginRequest loginRequest) throws InvalidUserNameOrPasswordException;

    AtikuResponse addArticle(AtikuRequest request) throws ArticleRequestException;

    UserBlogResponse displayUserBlog(String userName);

    DeleteArticleResponse deleteArticle(DeleteArticleRequest articleToDelete) throws ArticleRequestException;

    CommentResponse addComment(CommentRequest commentRequest);

    int getNumberOfArticles();

    int getNumberOfUserBlogs();

    BlogResponse deleteBlog(BlogRequest blogRequest);

    CommentResponse deleteComment(DeleteCommentRequest deleteCommentRequest);

    List<Atiku> getUserBlogArticles(BlogRequest blogRequest2);
}
