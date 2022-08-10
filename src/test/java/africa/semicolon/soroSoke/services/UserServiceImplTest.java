package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.repositories.BlogRepository;
import africa.semicolon.soroSoke.data.repositories.UserRepository;
import africa.semicolon.soroSoke.dtos.requests.*;
import africa.semicolon.soroSoke.exceptions.ArticleRequestException;
import africa.semicolon.soroSoke.exceptions.BlogExistsException;
import africa.semicolon.soroSoke.exceptions.InvalidUserNameOrPasswordException;
import africa.semicolon.soroSoke.exceptions.UserExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AtikuService atikuService;
    @Autowired
    private BlogService blogService;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        blogRepository.deleteAll();
    }

    @BeforeEach
    void setUp() throws UserExistsException {
        RegisterRequest registerUserForm = new RegisterRequest();
        registerUserForm.setEmail("boyo");
        registerUserForm.setPassword("amanpia2023");
        userService.registerUser(registerUserForm);
    }

    @Test
    void registerUser_repositorySizeIsOneTest() {
        assertEquals(1L, userRepository.count());
    }

    @Test
    void registerSameUserTwoTimes_repositorySizeIsOneTest() {
        RegisterRequest registerUserForm2 = new RegisterRequest();
        registerUserForm2.setEmail("boyo");
        registerUserForm2.setPassword("amanpia2023");
        UserExistsException user = assertThrows(UserExistsException.class,
                () -> userService.registerUser(registerUserForm2));
        assertEquals("boyo already exists. Login to create or access blog.", user.getMessage());
        assertEquals(1L, userRepository.count());
    }

    @Test
    void userLoginTest() throws InvalidUserNameOrPasswordException {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("amanpia2023");
        loginRequest.setUserName("Boyo");
        var response = userService.userLogin(loginRequest);
        assertEquals("Welcome Boyo!", response.getMessage());
    }

    @Test
    void userIncorrectLoginDetailsExceptionTest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("Amanpia2023");
        loginRequest.setUserName("Boyo");
        InvalidUserNameOrPasswordException logged = assertThrows(InvalidUserNameOrPasswordException.class,
                () -> userService.userLogin(loginRequest));
        assertEquals("Username or password incorrect. Try again", logged.getMessage());
    }

    @Test
    void userCanCreateNewBlogTest() throws BlogExistsException {
        AddBlogRequest blogRequest = new AddBlogRequest();
        blogRequest.setBlogTitle("Programming is Hard");
        blogRequest.setUserName("BoYo");
        userService.createNewBlog(blogRequest);
        assertEquals(1, userService.getNumberOfUserBlogs());
    }

    @Test
    void userCanOnlyCreateBlogOnceTest() throws BlogExistsException {
        AddBlogRequest blogRequest = new AddBlogRequest();
        blogRequest.setBlogTitle("Programming is Hard");
        blogRequest.setUserName("boyo");
        userService.createNewBlog(blogRequest);
        assertEquals(1, userService.getNumberOfUserBlogs());

        AddBlogRequest blogRequest2 = new AddBlogRequest();
        blogRequest2.setBlogTitle("How to make $1 billion");
        blogRequest2.setUserName("boyo");
//        BlogExistsException blog = assertThrows(BlogExistsException.class,
//                () -> userService.createNewBlog(blogRequest2));
        NullPointerException blog = assertThrows(NullPointerException.class,
                () -> userService.createNewBlog(blogRequest2));
        assertEquals("User BOYO has a blog.", blog.getMessage());
        assertEquals(1, userService.getNumberOfUserBlogs());
    }

    @Test
    void testThatUserCanUpdateBlogTitle() {
        AddBlogRequest blogRequest = new AddBlogRequest();
        blogRequest.setBlogTitle("Programming Is HARD");
        blogRequest.setUserName("boyo");
        userService.createNewBlog(blogRequest);
        assertEquals(1L, userService.getNumberOfUserBlogs());

        AddBlogRequest blogRequest2 = new AddBlogRequest();
        blogRequest2.setEditTitle("Programming takes consistency, time & patience");
        blogRequest2.setUserName("boyo");
        var savedBlog = userService.createNewBlog(blogRequest2);
        assertEquals(1L, userService.getNumberOfUserBlogs());
        assertEquals("Programming Is HARD blog title successfully updated with " +
                     "Programming takes consistency, time & patience", savedBlog.getMessage());
    }

    @Test
    void testThatUserCanCreateBlogAndAddArticle() throws ArticleRequestException {
        AddBlogRequest blogRequest = new AddBlogRequest();
        blogRequest.setBlogTitle("All About Programming");
        blogRequest.setUserName("boyo");
        userService.createNewBlog(blogRequest);
        assertEquals(1, blogService.getNumberOfUserBlogs());

        AtikuRequest newRequest = new AtikuRequest();
        newRequest.setTitle("Welcome to Programming 101");
        newRequest.setBody("Here we are going to go on a journey to introduce you to the world of programming.");
        newRequest.setUserName("Boyo");
        userService.addArticle(newRequest);
        assertEquals("Welcome to Programming 101", userRepository.findUserByUserNameIgnoreCase("Boyo").
                getBlog().getArticles().get(0).getTitle());
        assertEquals(1L, userService.getNumberOfUserBlogs());
    }

    @Test
    void testToDeleteAnArticle() throws ArticleRequestException {
        AddBlogRequest blogRequest = new AddBlogRequest();
        blogRequest.setBlogTitle("All About Programming");
        blogRequest.setUserName("boyo");
        userService.createNewBlog(blogRequest);
        assertEquals(1, userService.getNumberOfUserBlogs());

        AtikuRequest newRequest = new AtikuRequest();
        newRequest.setTitle("How to Think Like A Programmer");
        newRequest.setBody("The power of the mind is the ability to stretch the thinking muscle.");
        newRequest.setUserName("Boyo");
        userService.addArticle(newRequest);

        AtikuRequest newRequest2 = new AtikuRequest();
        newRequest2.setTitle("Welcome to Programming 101");
        newRequest2.setBody("Here we are going to go on a journey to introduce you to the world of programming.");
        newRequest2.setUserName("Boyo");
        userService.addArticle(newRequest2);
        assertEquals(2, userService.getNumberOfArticles());

        DeleteArticleRequest delArticle = new DeleteArticleRequest();
        delArticle.setUserName("BoyO");
        delArticle.setArticleId("Welcome to Programming 101");
        userService.deleteArticle(delArticle);

        assertEquals(1, userService.getNumberOfArticles());
        assertEquals(1,userService.getBlog("BOYO").getArticles().size());
    }

    @Test
    void testToAddACommentToArticle() throws ArticleRequestException {
        AddBlogRequest blogRequest = new AddBlogRequest();
        blogRequest.setBlogTitle("All About Programming");
        blogRequest.setUserName("boyo");
        userService.createNewBlog(blogRequest);
        assertEquals(1, userService.getNumberOfUserBlogs());

        AtikuRequest newRequest = new AtikuRequest();
        newRequest.setTitle("Welcome to Programming 101");
        userService.addArticle(newRequest);

        AtikuRequest newRequest2 = new AtikuRequest();
        newRequest2.setTitle("How to Think Like A Programmer");
        userService.addArticle(newRequest2);
        assertEquals(2, userService.getNumberOfArticles());

        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setCommentMessage("First step to be a programmer is to change your thinking pattern.");
        commentRequest.setArticleTitle("How to Think Like A Programmer");
        var blog = userService.addComment(commentRequest);
        assertEquals(1, userService.getNumberOfUserBlogs());

        System.out.println(blog.getArticles().get(0));
//        assertEquals(" ", userService.getBlog().get(0).getArticles());

    }
}