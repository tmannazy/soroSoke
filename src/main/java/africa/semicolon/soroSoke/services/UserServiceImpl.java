package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Atiku;
import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.data.models.User;
import africa.semicolon.soroSoke.data.repositories.UserRepository;
import africa.semicolon.soroSoke.dtos.requests.AddBlogRequest;
import africa.semicolon.soroSoke.dtos.requests.AtikuRequest;
import africa.semicolon.soroSoke.dtos.requests.LoginRequest;
import africa.semicolon.soroSoke.dtos.requests.RegisterRequest;
import africa.semicolon.soroSoke.dtos.responses.BlogResponse;
import africa.semicolon.soroSoke.dtos.responses.LoginResponse;
import africa.semicolon.soroSoke.dtos.responses.RegisterUserResponse;
import africa.semicolon.soroSoke.exceptions.BlogExistsException;
import africa.semicolon.soroSoke.exceptions.InvalidUserNameOrPasswordException;
import africa.semicolon.soroSoke.exceptions.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Blog addArticle(AtikuRequest request) {
        Atiku newAtiku = new Atiku();
        var foundBlog = blogService.getBlog();
        newAtiku.setTitle(request.getTitle());
        newAtiku.setTime(request.getTime());
        atikuService.saveArticle(newAtiku);
        foundBlog.getArticles().add(newAtiku);
        return foundBlog;
    }

    @Override
    public Blog getBlog() {
        return blogService.getBlog();
    }

}
