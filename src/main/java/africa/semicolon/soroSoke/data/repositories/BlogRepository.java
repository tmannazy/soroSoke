package africa.semicolon.soroSoke.data.repositories;

import africa.semicolon.soroSoke.data.models.Blog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogRepository extends MongoRepository<Blog, String> {
    Blog findBlogByBlogTitleContainingIgnoreCase(String blogName);
}
