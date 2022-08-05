package africa.semicolon.soroSoke.data.repositories;

import africa.semicolon.soroSoke.data.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
//@DataMongoTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void saveTest() {
        User user = new User();
        var savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());
    }

}