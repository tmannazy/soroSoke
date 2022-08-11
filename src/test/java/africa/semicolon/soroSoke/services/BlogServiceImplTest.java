package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.repositories.BlogRepository;
import africa.semicolon.soroSoke.data.repositories.UserRepository;
import africa.semicolon.soroSoke.dtos.requests.AddBlogRequest;
import africa.semicolon.soroSoke.dtos.requests.AtikuRequest;
import africa.semicolon.soroSoke.exceptions.BlogExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BlogServiceImplTest {
    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        blogRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testForNewBlog_blogSizeIsOne() {
        AddBlogRequest blogRequest = new AddBlogRequest();
        blogRequest.setBlogTitle("Programming Is HARD");
        blogRequest.setUserName("Tman");
        blogRequest.setPassword("taLLest@2023");
        blogService.saveBlog(blogRequest);
        assertEquals(1L, blogRepository.count());
    }


    @Test
    void testToCreateArticle() {
//        blogService.addArticle(AtikuRequest newArticle);
    }

}