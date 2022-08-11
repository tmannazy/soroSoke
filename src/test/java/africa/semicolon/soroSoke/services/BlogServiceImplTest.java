package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.repositories.BlogRepository;
import africa.semicolon.soroSoke.data.repositories.UserRepository;
import africa.semicolon.soroSoke.dtos.requests.BlogRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setBlogTitle("Programming Is HARD");
        blogRequest.setUserName("Tman");
        blogRequest.setPassword("taLLest@2023");
        blogService.saveBlog(blogRequest);
        assertEquals(1L, blogRepository.count());
    }


    @Test
    void testToDeleteBlog() {
        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setBlogTitle("Programming Is HARD");
        blogRequest.setUserName("Tman");
        blogRequest.setPassword("taLLest@2023");
        blogService.saveBlog(blogRequest);
        assertEquals(1L, blogRepository.count());

        BlogRequest blogRequest2 = new BlogRequest();
        blogRequest2.setBlogTitle("Programming is hard");
        blogRequest2.setUserName("tman");
        blogRequest2.setPassword("taLLest@2023");
        blogService.deleteBlog(blogRequest2);
        assertEquals(0, blogRepository.count());
    }

}