package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.repositories.AtikuRepository;
import africa.semicolon.soroSoke.data.repositories.BlogRepository;
import africa.semicolon.soroSoke.data.repositories.CommentRepository;
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
    private AtikuRepository atikuRepository;

    @Autowired
    private AtikuService atikuService;
    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        blogRepository.deleteAll();
        atikuRepository.deleteAll();
        commentRepository.deleteAll();
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
        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setBlogTitle("Programming is Hard");
        blogRequest.setUserName("BoYo");
        blogRequest.setPassword("amanpia2023");
        userService.createNewBlog(blogRequest);
        assertEquals(1, userService.getNumberOfUserBlogs());
    }

    @Test
    void userCanOnlyCreateBlogOnceTest() throws BlogExistsException {
        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setBlogTitle("Programming is Hard");
        blogRequest.setUserName("boyo");
        blogRequest.setPassword("amanpia2023");
        userService.createNewBlog(blogRequest);
        assertEquals(1, userService.getNumberOfUserBlogs());

        BlogRequest blogRequest2 = new BlogRequest();
        blogRequest2.setBlogTitle("Programming is HARd");
        blogRequest2.setUserName("boyo");
        blogRequest.setPassword("amanpia2023");
        BlogExistsException blog = assertThrows(BlogExistsException.class,
                () -> userService.createNewBlog(blogRequest2));
        assertEquals("User BOYO has a blog.", blog.getMessage());
        assertEquals(1, userService.getNumberOfUserBlogs());
    }

    @Test
    void testThatUserCanUpdateBlogTitle() {
        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setBlogTitle("Programming Is HARD");
        blogRequest.setUserName("boyo");
        blogRequest.setPassword("amanpia2023");
        userService.createNewBlog(blogRequest);
        assertEquals(1L, userService.getNumberOfUserBlogs());

        BlogRequest blogRequest2 = new BlogRequest();
        blogRequest2.setBlogTitle("Programming takes consistency, time & patience");
        blogRequest2.setUserName("boyo");
        blogRequest2.setPassword("amanpia2023");
        var savedBlog = userService.createNewBlog(blogRequest2);
        assertEquals(1L, userService.getNumberOfUserBlogs());
        assertEquals("Programming Is HARD blog title successfully updated with " +
                     "Programming takes consistency, time & patience", savedBlog.getMessage());
    }

    @Test
    void testThatUserCanCreateBlogAndAddArticle() throws ArticleRequestException {
        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setBlogTitle("All About Programming");
        blogRequest.setUserName("boyo");
        blogRequest.setPassword("amanpia2023");
        userService.createNewBlog(blogRequest);
        assertEquals(1, blogService.getNumberOfUserBlogs());

        AtikuRequest newRequest = new AtikuRequest();
        newRequest.setTitle("Welcome to Programming 101");
        newRequest.setBody("Here we are going to go on a journey to introduce you to the world of programming.");
        newRequest.setUserName("Boyo");
        newRequest.setPassword("amanpia2023");
        userService.addArticle(newRequest);

        AtikuRequest newRequest2 = new AtikuRequest();
        newRequest2.setTitle("Welcome to Programming 101");
        newRequest2.setBody("Journey to introduce you to the world of programming.");
        newRequest2.setUserName("Boyo");
        newRequest2.setPassword("amanpia2023");
        ArticleRequestException article = assertThrows(ArticleRequestException.class,
                () -> userService.addArticle(newRequest2));
        assertEquals("'Welcome to Programming 101' already exists in Boyo's blog.", article.getMessage());
        assertEquals("Welcome to Programming 101", userRepository.findUserByUserNameIgnoreCase("Boyo").
                getBlog().getArticles().get(0).getTitle());
        assertEquals(1, userRepository.findUserByUserNameIgnoreCase("Boyo").
                getBlog().getArticles().size());
        assertEquals(1L, userService.getNumberOfUserBlogs());
    }

    @Test
    void testToDeleteAnArticle() throws ArticleRequestException {
        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setBlogTitle("All About Programming");
        blogRequest.setUserName("boyo");
        blogRequest.setPassword("amanpia2023");
        userService.createNewBlog(blogRequest);
        assertEquals(1, userService.getNumberOfUserBlogs());

        AtikuRequest newRequest = new AtikuRequest();
        newRequest.setTitle("How to Think Like A Programmer");
        newRequest.setBody("The power of the mind is the ability to stretch the thinking muscle.");
        newRequest.setUserName("Boyo");
        newRequest.setPassword("amanpia2023");
        userService.addArticle(newRequest);

        AtikuRequest newRequest2 = new AtikuRequest();
        newRequest2.setTitle("Welcome to Programming 101");
        newRequest2.setBody("Here we are going to go on a journey to introduce you to the world of programming.");
        newRequest2.setUserName("Boyo");
        newRequest2.setPassword("amanpia2023");
        userService.addArticle(newRequest2);
        assertEquals(2, userService.getNumberOfArticles());

        DeleteArticleRequest delArticle = new DeleteArticleRequest();
        delArticle.setUserName("BoyO");
        delArticle.setPassword("amanpia2023");
        delArticle.setArticleId(userService.displayUserBlog("Boyo").getArticles().get(1).getId());
        userService.deleteArticle(delArticle);
        assertEquals(1, userService.getNumberOfArticles());
        assertEquals(1, userService.displayUserBlog("BOYO").getArticles().size());
    }

    @Test
    void testToAddACommentToArticle() throws ArticleRequestException {
        RegisterRequest registerUserForm = new RegisterRequest();
        registerUserForm.setEmail("Plato");
        registerUserForm.setPassword("@Platonic");
        userService.registerUser(registerUserForm);

        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setBlogTitle("Like, Love or Lust - The 3ple Ls of Life");
        blogRequest.setUserName("plaTo");
        blogRequest.setPassword("@Platonic");
        userService.createNewBlog(blogRequest);
        assertEquals(1, userService.getNumberOfUserBlogs());

        AtikuRequest newRequest = new AtikuRequest();
        newRequest.setTitle("What is Like?");
        newRequest.setBody("Humans are creatures of emotions but how long can it last?");
        newRequest.setUserName("pLatO");
        newRequest.setPassword("@Platonic");
        userService.addArticle(newRequest);

        AtikuRequest newRequest2 = new AtikuRequest();
        newRequest2.setTitle("How bad can Lust deceive you thinking you Love?");
        newRequest2.setBody("It always runs in one's blood stream. There is an urge to satisfy.");
        newRequest2.setUserName("pLatO");
        newRequest2.setPassword("@Platonic");
        userService.addArticle(newRequest2);
        assertEquals(2, userService.getNumberOfArticles());

        BlogRequest blogRequest2 = new BlogRequest();
        blogRequest2.setBlogTitle("All About Programming");
        blogRequest2.setUserName("boyo");
        blogRequest2.setPassword("amanpia2023");
        userService.createNewBlog(blogRequest2);
        assertEquals(2, userService.getNumberOfUserBlogs());

        AtikuRequest newRequest3 = new AtikuRequest();
        newRequest3.setTitle("How to Think Like A Programmer");
        newRequest3.setBody("The power of the mind is the ability to stretch the thinking muscle.");
        newRequest3.setUserName("Boyo");
        newRequest3.setPassword("amanpia2023");
        userService.addArticle(newRequest3);
        assertEquals(3, userService.getNumberOfArticles());

        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setCommentMessage("First step to be a programmer is to change your thinking pattern.");
        commentRequest.setArticleId(atikuService.getAllArticles().get(2).getId());
        commentRequest.setUserName("Boyo");
        userService.addComment(commentRequest);
        assertEquals(2, userService.getNumberOfUserBlogs());
        assertEquals("First step to be a programmer is to change your thinking pattern.", userService.displayUserBlog("Boyo").
                getArticles().get(0).getComments().get(0).getCommentBody());
    }

    @Test
    void testToDeleteACommentFromArticle() {
        RegisterRequest registerUserForm = new RegisterRequest();
        registerUserForm.setEmail("Plato");
        registerUserForm.setPassword("@Platonic");
        userService.registerUser(registerUserForm);

        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setBlogTitle("Like, Love or Lust - The 3ple Ls of Life");
        blogRequest.setUserName("plaTo");
        blogRequest.setPassword("@Platonic");
        userService.createNewBlog(blogRequest);
        assertEquals(1, userService.getNumberOfUserBlogs());

        AtikuRequest newRequest = new AtikuRequest();
        newRequest.setTitle("What is Like?");
        newRequest.setBody("Humans are creatures of emotions but how long can it last?");
        newRequest.setUserName("pLatO");
        newRequest.setPassword("@Platonic");
        userService.addArticle(newRequest);

        AtikuRequest newRequest2 = new AtikuRequest();
        newRequest2.setTitle("How bad can Lust deceive you thinking you Love?");
        newRequest2.setBody("It always runs in one's blood stream. There is an urge to satisfy.");
        newRequest2.setUserName("pLatO");
        newRequest2.setPassword("@Platonic");
        userService.addArticle(newRequest2);
        assertEquals(2, userService.getNumberOfArticles());

        DeleteArticleRequest deleteArticleRequest = new DeleteArticleRequest();
        deleteArticleRequest.setArticleId(userService.displayUserBlog("Plato").getArticles().get(1).getId());
        deleteArticleRequest.setUserName("PLATO");
        deleteArticleRequest.setPassword("@Platonic");
        userService.deleteArticle(deleteArticleRequest);
        assertEquals(1, userService.getNumberOfArticles());
    }

    @Test
    void testToDeleteUserBlog() {
        RegisterRequest registerUserForm = new RegisterRequest();
        registerUserForm.setEmail("Plato");
        registerUserForm.setPassword("@Platonic");
        userService.registerUser(registerUserForm);

        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setBlogTitle("Like, Love or Lust - The 3ple Ls of Life");
        blogRequest.setUserName("plaTo");
        blogRequest.setPassword("@Platonic");
        userService.createNewBlog(blogRequest);
        assertEquals(1, userService.getNumberOfUserBlogs());

        BlogRequest blogRequest2 = new BlogRequest();
        blogRequest2.setBlogTitle("Programming Is HARD");
        blogRequest2.setUserName("Tman");
        blogRequest2.setPassword("taLLest@2023");
        userService.deleteBlog(blogRequest);
        assertEquals(0, userService.getNumberOfUserBlogs());
    }

    @Test
    void testToDeleteArticleComment() {
        BlogRequest blogRequest2 = new BlogRequest();
        blogRequest2.setBlogTitle("All About Programming");
        blogRequest2.setUserName("boyo");
        blogRequest2.setPassword("amanpia2023");
        userService.createNewBlog(blogRequest2);
        assertEquals(1, userService.getNumberOfUserBlogs());

        AtikuRequest newRequest3 = new AtikuRequest();
        newRequest3.setTitle("How to Think Like A Programmer");
        newRequest3.setBody("The power of the mind is the ability to stretch the thinking muscle.");
        newRequest3.setUserName("Boyo");
        newRequest3.setPassword("amanpia2023");
        userService.addArticle(newRequest3);
        assertEquals(1, userService.getNumberOfArticles());

        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setCommentMessage("First step to be a programmer is to change your thinking pattern.");
        commentRequest.setArticleId(atikuService.getAllArticles().get(0).getId());
        commentRequest.setUserName("Boyo");
        userService.addComment(commentRequest);

        CommentRequest commentRequest2 = new CommentRequest();
        commentRequest2.setCommentMessage("Good to go most times is the first step of life.");
        commentRequest2.setArticleId(atikuService.getAllArticles().get(0).getId());
        commentRequest2.setUserName("Boyo");
        userService.addComment(commentRequest2);
        assertEquals(2, userService.displayUserBlog("Boyo").
                getArticles().get(0).getComments().size());

        DeleteCommentRequest deleteCommentRequest = new DeleteCommentRequest();
        deleteCommentRequest.setCommentId(commentService.getAllComments().get(0).getId());
        deleteCommentRequest.setUserName("BOYO");
        deleteCommentRequest.setPassword("amanpia2023");
        userService.deleteComment(deleteCommentRequest);
        assertEquals(1, userService.displayUserBlog("Boyo").
                getArticles().get(0).getComments().size());
    }
}