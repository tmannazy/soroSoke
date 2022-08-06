package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Blog;
import africa.semicolon.soroSoke.data.repositories.BlogRepository;
import africa.semicolon.soroSoke.dtos.requests.AddBlogRequest;
import africa.semicolon.soroSoke.exceptions.BlogExistsException;
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

    @Test
    void testForNewBlog_blogSizeIsOneTest() throws BlogExistsException {
        AddBlogRequest blogRequest = new AddBlogRequest();
        blogRequest.setBlogTitle("Programming Is HARD");
        blogRequest.setUserName("Tman");
        Blog blog = new Blog();
        blog.setBlogTitle(blogRequest.getBlogTitle());
        blogService.saveBlog(blog);
        assertEquals(1L, blogRepository.count());

        AddBlogRequest blogRequest2 = new AddBlogRequest();
        blogRequest2.setBlogTitle("Programming Is Hard");
        blogRequest2.setUserName("Nazy");
        Blog blog2 = new Blog();
        blog2.setBlogTitle(blogRequest2.getBlogTitle());
        BlogExistsException blogExist = assertThrows(BlogExistsException.class,
                () -> blogService.saveBlog(blog2));
        assertEquals(" ", blogExist.getMessage());
//        assertThrows(BlogExistsException.class, ()->{})
        blogService.saveBlog(blog2);
        assertEquals(1L, blogRepository.count());
    }


}