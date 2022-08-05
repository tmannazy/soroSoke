package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.repositories.UserRepository;
import africa.semicolon.soroSoke.dtos.requests.AddBlogRequest;
import africa.semicolon.soroSoke.dtos.requests.RegisterRequest;
import africa.semicolon.soroSoke.exceptions.BlogTitleExists;
import africa.semicolon.soroSoke.exceptions.UserExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class UserServiceImplTest {
    private RegisterRequest registerUserForm;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogService blogService;

    @BeforeEach
    void setUp() {
        registerUserForm = new RegisterRequest();
    }

    @Test
    void registerUser_repositorySizeIsOneTest() throws UserExistsException {
        registerUserForm.setEmail("dorcas");
        registerUserForm.setPassword("iLoveJesus2022");
        userService.registerUser(registerUserForm);
        assertEquals(1L, userRepository.count());
    }

    @Test
    void registerSameUserTwoTimes_repositorySizeIsOneTest() throws UserExistsException {
        registerUserForm.setEmail("dorcas");
        registerUserForm.setPassword("iLoveJesus2022");
        userService.registerUser(registerUserForm);
        RegisterRequest registerUserForm2 = new RegisterRequest();
        registerUserForm2.setEmail("dorcas");
        registerUserForm2.setPassword("iLoveJesus2022");
        try {
            userService.registerUser(registerUserForm2);
        } catch (UserExistsException err) {
            System.out.println(err.getMessage());
        }
        assertEquals(1L, userRepository.count());
    }

    @Test
    void userCanCreateNewBlogTest() throws UserExistsException, BlogTitleExists {
        registerUserForm.setEmail("dorcas");
        registerUserForm.setPassword("iLoveJesus2022");
        userService.registerUser(registerUserForm);
        AddBlogRequest blogRequest = new AddBlogRequest();
        blogRequest.setBlogTitle("Programming is Hard");
        blogRequest.setUserName("dorcas");
        userService.createNewBlog(blogRequest);
        assertEquals(1,blogService.getNumberOfUserBlogs());
    }

}