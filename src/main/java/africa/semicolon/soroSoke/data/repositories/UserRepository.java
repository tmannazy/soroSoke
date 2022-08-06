package africa.semicolon.soroSoke.data.repositories;

import africa.semicolon.soroSoke.data.models.User;
import africa.semicolon.soroSoke.dtos.responses.LoginResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findUserByUserNameIgnoreCase(String name);
}
