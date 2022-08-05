package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.data.models.User;
import africa.semicolon.soroSoke.data.repositories.UserRepository;
import africa.semicolon.soroSoke.dtos.requests.AddBlogRequest;
import africa.semicolon.soroSoke.dtos.requests.RegisterRequest;
import africa.semicolon.soroSoke.dtos.responses.BlogResponse;
import africa.semicolon.soroSoke.dtos.responses.RegisterUserResponse;
import africa.semicolon.soroSoke.exceptions.BlogTitleAlreadyExists;
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
    private final BlogResponse blogResponse = new BlogResponse();

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
    public BlogResponse createNewBlog(AddBlogRequest createBlog) throws BlogTitleAlreadyExists {
        Blog newBlog = new Blog();
        var validateUser = userRepository.findUserByUserName(createBlog.getUserName());
        var savedBlog = blogService.saveBlog(createBlog);
        newBlog.setBlogTitle(createBlog.getBlogTitle());
        validateUser.getBlogs().add(newBlog);
        userRepository.save(validateUser);
        return savedBlog;
    }


}
