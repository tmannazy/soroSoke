package africa.semicolon.soroSoke.data.repositories;

import africa.semicolon.soroSoke.data.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
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