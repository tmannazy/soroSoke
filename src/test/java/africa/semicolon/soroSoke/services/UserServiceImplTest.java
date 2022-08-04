package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.repositories.UserRepository;
import africa.semicolon.soroSoke.dtos.requests.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void registerUser_repositorySizeIsOneTest() {
        RegisterRequest registerUserForm = new RegisterRequest();
        registerUserForm.setEmail("dorcas");
        registerUserForm.setPassword("iLoveJesus2022");
        userService.registerUser(registerUserForm);

        assertEquals(1L, userRepository.count());
    }

}