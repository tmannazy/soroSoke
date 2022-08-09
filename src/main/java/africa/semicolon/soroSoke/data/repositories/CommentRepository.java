package africa.semicolon.soroSoke.data.repositories;

import africa.semicolon.soroSoke.data.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
