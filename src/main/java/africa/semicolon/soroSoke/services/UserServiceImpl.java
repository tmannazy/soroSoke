package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.data.models.User;
import africa.semicolon.soroSoke.data.repositories.UserRepository;
import africa.semicolon.soroSoke.dtos.requests.AddBlogRequest;
import africa.semicolon.soroSoke.dtos.requests.LoginRequest;
import africa.semicolon.soroSoke.dtos.requests.RegisterRequest;
import africa.semicolon.soroSoke.dtos.responses.BlogResponse;
import africa.semicolon.soroSoke.dtos.responses.LoginResponse;
import africa.semicolon.soroSoke.dtos.responses.RegisterUserResponse;
import africa.semicolon.soroSoke.exceptions.BlogExistsException;
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
        return response;
    }

    @Override
    public BlogResponse createNewBlog(AddBlogRequest createBlog) throws BlogExistsException {
        var validateUser = userRepository.findUserByUserNameIgnoreCase(createBlog.getUserName());
        var validateBlogName = blogService.checkIfUserHasBlog();
        if (validateBlogName) throw new BlogExistsException(validateUser.getUserName() + " has a blog.");
        Blog newBlog = new Blog();
        newBlog.setBlogTitle(createBlog.getBlogTitle());
        blogService.saveBlog(newBlog);
        userRepository.save(validateUser);

        BlogResponse blogResponse = new BlogResponse();
        blogResponse.setMessage("User with " + createBlog.getBlogTitle() + " successfully registered.");
        return blogResponse;
    }

    @Override
    public LoginResponse userLogin(LoginRequest loginRequest) {
        var user = userRepository.findUserByUserNameIgnoreCase(loginRequest.getUserName());
        LoginResponse loginResponse = new LoginResponse();
        if (user == null) {
            loginResponse.setMessage("Username or password incorrect. Try again");
            return null;
        }
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            loginResponse.setMessage("Username or password incorrect. Try again");
        } else {
            loginResponse.setMessage("Welcome " + loginRequest.getUserName());
        }
        return loginResponse;
    }

}


//}
