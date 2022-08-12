package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.data.models.User;
import africa.semicolon.soroSoke.data.repositories.UserRepository;
import africa.semicolon.soroSoke.dtos.requests.*;
import africa.semicolon.soroSoke.dtos.responses.*;
import africa.semicolon.soroSoke.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private RegisterUserResponse response = new RegisterUserResponse();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

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
    public BlogResponse createNewBlog(BlogRequest createBlog) throws BlogExistsException {
        BlogResponse blogResponse = new BlogResponse();

        var validateUser = userRepository.findUserByUserNameIgnoreCase(createBlog.getUserName());
        if (validateUser == null) {
            throw new BlogExistsException(createBlog.getUserName() + " does not exist! Create an account.");
        }

        var userPass = Objects.equals(validateUser.getPassword(), createBlog.getPassword());
        if (!userPass) {
            throw new InvalidUserNameOrPasswordException("Username or Password incorrect. Try again!");
        }

        if (validateUser.getBlog() == null) {
            var newBlog = blogService.saveBlog(createBlog);
            newBlog.setBlogTitle(createBlog.getBlogTitle());
            validateUser.setBlog(newBlog);
            userRepository.save(validateUser);
            blogResponse.setMessage("Blog with '" + createBlog.getBlogTitle() + "' successfully created.");
            return blogResponse;
        }

        if (validateUser.getBlog() != null) {
            if (validateUser.getBlog().getBlogTitle().equalsIgnoreCase(createBlog.getBlogTitle())) {
                throw new BlogExistsException("User " + createBlog.getUserName().toUpperCase() + " has a blog.");
            }
        }

        if (!validateUser.getBlog().getBlogTitle().isEmpty()) {
            var newBlog = blogService.getBlogByTitle(validateUser.getBlog().getBlogTitle());
            blogResponse.setMessage(validateUser.getBlog().getBlogTitle() +
                                    " blog title successfully updated with " + createBlog.getBlogTitle());
            validateUser.setBlog(newBlog);
            blogService.saveBlog(newBlog);
            userRepository.save(validateUser);
            return blogResponse;
        }


        blogResponse.setMessage("User not found");
        return blogResponse;
    }

    @Override
    public LoginResponse userLogin(LoginRequest loginRequest) throws InvalidUserNameOrPasswordException {
        var user = userRepository.findUserByUserNameIgnoreCase(loginRequest.getUserName());
        LoginResponse loginResponse = new LoginResponse();
        if (user == null) {
            throw new InvalidUserNameOrPasswordException("Username or password incorrect. Try again!");
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
        if (validateUser == null) {
            throw new UserExistsException("User details '" + request.getUserName() + "' entered does not exist.");
        }
        var userPass = Objects.equals(validateUser.getPassword(), request.getPassword());
        if (!userPass) {
            throw new InvalidUserNameOrPasswordException("Username or Password incorrect. Try again!");
        }
        var checkBlog = validateUser.getBlog();
        if (checkBlog.getBlogTitle() != null) {
            addArticleToUser(request, atikuResponse, validateUser, checkBlog);
        } else {
            throw new ArticleRequestException(request.getUserName() + " is yet to create a Blog.");
        }
        return atikuResponse;
    }

    private void addArticleToUser(AtikuRequest request, AtikuResponse atikuResponse, User validateUser, Blog checkBlog) {
        var userArticles = checkBlog.getArticles();
        for (var article : userArticles) {
            if (article.getTitle().equalsIgnoreCase(request.getTitle())) {
                throw new ArticleRequestException("'" + request.getTitle() + "' already exists in " +
                                                  request.getUserName() + "'s blog.");
            }
        }
        var savedArticle = blogService.addArticle(request);
        userArticles.add(savedArticle);
        blogService.saveBlog(checkBlog);
        userRepository.save(validateUser);
        atikuResponse.setMessage("Article added to " + request.getUserName() + "'s Blog.");
    }

    private void addArticleToUsesr(AtikuRequest request, AtikuResponse atikuResponse, User validateUser) {
        var userBlog = validateUser.getBlog();
        var savedArticle = blogService.addArticle(request);
        userBlog.getArticles().add(savedArticle);
        blogService.saveBlog(userBlog);
        userRepository.save(validateUser);
        atikuResponse.setMessage("Article added to " + request.getUserName() + "'s Blog.");
    }

    @Override
    public UserBlogResponse displayUserBlog(String userName) {
        var userFound = userRepository.findUserByUserNameIgnoreCase(userName);
        UserBlogResponse response = new UserBlogResponse();
        if (userFound != null) {
            response.setBlogTitle(userFound.getBlog().getBlogTitle());
            response.setId(userFound.getBlog().getId());
            response.setArticles(userFound.getBlog().getArticles());
        }
        return response;
    }

    @Override
    public DeleteArticleResponse deleteArticle(DeleteArticleRequest articleToDelete) throws ArticleRequestException {
        var userFound = userRepository.findUserByUserNameIgnoreCase(articleToDelete.getUserName());
        if (userFound == null) {
            throw new UserExistsException("User not found. Enter correct details.");
        }
        var userPass = Objects.equals(userFound.getPassword(), articleToDelete.getPassword());
        DeleteArticleResponse response = new DeleteArticleResponse();
        if (userPass) {
            var deletedArticle = atikuService.deleteArticle(articleToDelete.getArticleId());
            if (deletedArticle != null) {
                userRepository.save(userFound);
                response.setMessage("Article '" + deletedArticle.getTitle() + "' removed successfully");
            } else {
                throw new ArticleRequestException("The selected article don't exist.");
            }
        } else {
            throw new InvalidUserNameOrPasswordException("Username or Password incorrect.");
        }
        return response;
    }

    @Override
    public CommentResponse addComment(CommentRequest commentRequest) {
        var user = userRepository.findUserByUserNameIgnoreCase(commentRequest.getUserName());
        CommentResponse response = new CommentResponse();
        if (user != null) {
            addCommentToArticle(commentRequest, user, response);
        } else {
            throw new UserExistsException(commentRequest.getUserName() + "can't be found. Try again");
        }
        return response;
    }

    private void addCommentToArticle(CommentRequest commentRequest, User user, CommentResponse response) {
        var userBlog = user.getBlog();
        var articleList = userBlog.getArticles();
        for (var article : articleList) {
            if (Objects.equals(article.getId(), commentRequest.getArticleId())) {
                var savedComment = atikuService.saveComment(commentRequest);
                article.getComments().add(savedComment);
                break;
            }
        }
        blogService.saveBlog(userBlog);
        userRepository.save(user);
        response.setMessage("Comment successfully added to article.");
    }

    @Override
    public int getNumberOfArticles() {
        return atikuService.getNumberOfArticles();
    }

    @Override
    public int getNumberOfUserBlogs() {
        return blogService.getNumberOfUserBlogs();
    }

    @Override
    public BlogResponse deleteBlog(BlogRequest blogRequest) {
        var userFound = userRepository.findUserByUserNameIgnoreCase(blogRequest.getUserName());
        var userPass = Objects.equals(userFound.getPassword(), blogRequest.getPassword());
        BlogResponse response = new BlogResponse();
        if (userPass) {
            blogService.deleteBlog(blogRequest);
            response.setMessage(blogRequest.getUserName() + "'s '" +
                                blogRequest.getBlogTitle() + "' successfully removed");
        }
        response.setMessage("'" + blogRequest.getBlogTitle() + "' doest not exist.");
        return response;
    }

    @Override
    public CommentResponse deleteComment(DeleteCommentRequest deleteCommentRequest) {
        var userFound = userRepository.findUserByUserNameIgnoreCase(deleteCommentRequest.getUserName());
        if (userFound == null) {
            throw new UserExistsException("User not found. Enter correct details.");
        }
        var userPass = Objects.equals(userFound.getPassword(), deleteCommentRequest.getPassword());
        CommentResponse response = new CommentResponse();
        var comment = commentService.deleteComment(deleteCommentRequest);
        if (userPass) {
            if (comment.isEmpty()) {
                throw new CommentRequestException("Comment does not exist in '"
                                                  + deleteCommentRequest.getUserName() + "' blog");
            } else {
                response.setMessage("'" + comment + "' successfully removed from article.");
            }
        } else {
            throw new InvalidUserNameOrPasswordException("Username or Password incorrect.");
        }
        return response;
    }

}
