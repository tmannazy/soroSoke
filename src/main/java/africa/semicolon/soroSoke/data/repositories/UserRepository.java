package africa.semicolon.soroSoke.data.repositories;

import africa.semicolon.soroSoke.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findUserByUserNameIgnoreCase(String name);
}
