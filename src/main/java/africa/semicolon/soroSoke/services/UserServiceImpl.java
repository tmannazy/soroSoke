package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.data.models.User;
import africa.semicolon.soroSoke.data.repositories.UserRepository;
import africa.semicolon.soroSoke.dtos.requests.AddBlogRequest;
import africa.semicolon.soroSoke.dtos.requests.RegisterRequest;
import africa.semicolon.soroSoke.dtos.responses.BlogResponse;
import africa.semicolon.soroSoke.dtos.responses.RegisterUserResponse;
import africa.semicolon.soroSoke.exceptions.BlogTitleExists;
import africa.semicolon.soroSoke.exceptions.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogService blogService;

    private final RegisterUserResponse response = new RegisterUserResponse();

    @Override
    public RegisterUserResponse registerUser(RegisterRequest registerRequest) throws UserExistsException {
        User user = new User();
        var validateUser = userRepository.findUserByUserName(registerRequest.getEmail());
        if(validateUser != null){
            throw new UserExistsException(registerRequest.getEmail() + " already exists. Login to create or access blog.");
        }
        user.setUserName(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        userRepository.save(user);
        return response;
    }

    @Override
    public BlogResponse createNewBlog(AddBlogRequest createBlog) throws BlogTitleExists {
        var validateUser = userRepository.findUserByUserName(createBlog.getUserName());
        var validateBlogName = blogService.findIfBlogTitleExists(createBlog.getBlogTitle());
        if (validateBlogName != null) throw new BlogTitleExists(createBlog.getBlogTitle() + " already exists.");
        Blog newBlog = new Blog();
        newBlog.setBlogTitle(createBlog.getBlogTitle());
        var savedBlog = blogService.saveBlog(newBlog);
        validateUser.getBlogs().add(savedBlog);
        userRepository.save(validateUser);

        BlogResponse blogResponse = new BlogResponse();
        blogResponse.setMessage("User with " + createBlog.getBlogTitle() + " successfully registered.");
        return blogResponse;
    }


}
