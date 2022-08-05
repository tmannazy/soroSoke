package africa.semicolon.soroSoke.controllers;

import africa.semicolon.soroSoke.dtos.requests.RegisterRequest;
import africa.semicolon.soroSoke.dtos.responses.RegisterUserResponse;
import africa.semicolon.soroSoke.exceptions.UserExistsException;
import africa.semicolon.soroSoke.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    public UserService userService;

    @PostMapping("/user")
    public RegisterUserResponse registerUser(@RequestBody RegisterRequest request) throws UserExistsException {
        return userService.registerUser(request);
    }
}
