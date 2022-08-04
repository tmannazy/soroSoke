package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.User;
import africa.semicolon.soroSoke.data.repositories.UserRepository;
import africa.semicolon.soroSoke.dtos.requests.RegisterRequest;
import africa.semicolon.soroSoke.dtos.responses.RegisterUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public RegisterUserResponse registerUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        userRepository.save(user);
        return null;
    }
}
