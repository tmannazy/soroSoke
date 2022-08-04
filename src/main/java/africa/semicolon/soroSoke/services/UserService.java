package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.dtos.requests.RegisterRequest;
import africa.semicolon.soroSoke.dtos.responses.RegisterUserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    RegisterUserResponse registerUser(RegisterRequest registerRequest);


}
