package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.data.models.User;
import africa.semicolon.soroSoke.data.repositories.UserRepository;
import africa.semicolon.soroSoke.dtos.requests.*;
import africa.semicolon.soroSoke.dtos.responses.*;
import africa.semicolon.soroSoke.exceptions.ArticleRequestException;
import africa.semicolon.soroSoke.exceptions.BlogExistsException;
import africa.semicolon.soroSoke.exceptions.InvalidUserNameOrPasswordException;
import africa.semicolon.soroSoke.exceptions.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private final RegisterUserResponse response = new RegisterUserResponse();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BlogService blogService;

    @Autowired
    private AtikuService atikuService;

    @Override
    public RegisterUserResponse registerUser(RegisterRequest registerRequest) throws UserExistsException {
        User user = new User();
        var validateUser = userRepository.findUserByUserNameIgnoreCase(registerRequest.getEmail());
        if (validateUser != null) {
            throw new UserExistsException(registerRequest.getEmail() + " already exists. Login to create or access blog.");
        }
        user.setUserName(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        userRepository.save(user);
        response.setMessage(registerRequest.getEmail() + " successfully registered. Welcome!!!");
        return response;
    }

    @Override
    public BlogResponse createNewBlog(AddBlogRequest createBlog) throws BlogExistsException, NullPointerException {
        var validateUser = userRepository.findUserByUserNameIgnoreCase(createBlog.getUserName());
        BlogResponse blogResponse = new BlogResponse();
        if (validateUser == null) {
            throw new BlogExistsException(createBlog.getUserName() + " does not exist! Create an account.");
        }

        if (validateUser.getBlog() == null) {
            var newBlog = blogService.saveBlog(createBlog);
            newBlog.setBlogTitle(createBlog.getBlogTitle());
            validateUser.setBlog(newBlog);
            userRepository.save(validateUser);
            blogResponse.setMessage("Blog with " + createBlog.getBlogTitle() + " successfully created.");
            return blogResponse;
        }

        if (validateUser.getBlog() != null && createBlog.getEditTitle() == null) {
            throw new NullPointerException("User " + createBlog.getUserName().toUpperCase() + " has a blog.");
        }

        if (!validateUser.getBlog().getBlogTitle().isEmpty()) {
            var newBlog = blogService.getBlogByTitle(validateUser.getBlog().getBlogTitle());
            blogResponse.setMessage(validateUser.getBlog().getBlogTitle() +
                                    " blog title successfully updated with " + createBlog.getEditTitle());
            validateUser.setBlog(newBlog);
            blogService.deleteBlog(newBlog);
            blogService.saveBlog(createBlog);
            userRepository.save(validateUser);
            return blogResponse;
        }
        return null;
    }

    @Override
    public LoginResponse userLogin(LoginRequest loginRequest) throws InvalidUserNameOrPasswordException {
        var user = userRepository.findUserByUserNameIgnoreCase(loginRequest.getUserName());
        LoginResponse loginResponse = new LoginResponse();
        if (user == null) {
            throw new InvalidUserNameOrPasswordException("Username or password incorrect. Try again");
        }
        if (!Objects.equals(user.getPassword(), loginRequest.getPassword())) {
            throw new InvalidUserNameOrPasswordException("Username or password incorrect. Try again");
        } else {
            loginResponse.setMessage("Welcome " + loginRequest.getUserName() + "!");
        }
        return loginResponse;
    }

    @Override
    public AtikuResponse addArticle(AtikuRequest request) throws ArticleRequestException {
        AtikuResponse atikuResponse = new AtikuResponse();
        var validateUser = userRepository.findUserByUserNameIgnoreCase(request.getUserName());
        if (validateUser != null) {
            var checkBlog = validateUser.getBlog().getBlogTitle();
            if(checkBlog != null) {
                addArticleToUser(request, atikuResponse, validateUser);
            }
        } else{
            throw new ArticleRequestException(request.getUserName() + " is yet to create a Blog.");
        }
        return atikuResponse;
    }

    private void addArticleToUser(AtikuRequest request, AtikuResponse atikuResponse, User validateUser) {
        var userBlog = validateUser.getBlog();
        var savedArticle = blogService.addArticle(request);
        userBlog.getArticles().add(savedArticle);
        blogService.saveBlog(userBlog);
        userRepository.save(validateUser);
        atikuResponse.setMessage("Article added to " + request.getUserName() + "'s Blog.");
    }

    @Override
    public List<AllBlogResponse> getBlog() {
//       var allBlog = blogService.getBlog();
        return null;
    }

    @Override
    public void deleteArticle(String articleToDelete) {
        atikuService.deleteArticle(articleToDelete);
    }

    @Override
    public Blog addComment(CommentRequest commentRequest) {
        var savedArticle = atikuService.saveComment(commentRequest);
        var foundBlog = blogService.getBlog().get(0);
        foundBlog.getArticles().add(savedArticle);
        return foundBlog;
    }

}
