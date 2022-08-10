package africa.semicolon.soroSoke.controllers;

import africa.semicolon.soroSoke.dtos.requests.AddBlogRequest;
import africa.semicolon.soroSoke.dtos.requests.LoginRequest;
import africa.semicolon.soroSoke.dtos.requests.RegisterRequest;
import africa.semicolon.soroSoke.exceptions.BlogExistsException;
import africa.semicolon.soroSoke.exceptions.InvalidUserNameOrPasswordException;
import africa.semicolon.soroSoke.exceptions.UserExistsException;
import africa.semicolon.soroSoke.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    public UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            var serviceResponse = userService.registerUser(registerRequest);
            return new ResponseEntity<>(serviceResponse, HttpStatus.CREATED);
        } catch (UserExistsException err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> accountLogin(@RequestBody LoginRequest loginRequest) {
        try {
            var loginResponse = userService.userLogin(loginRequest);
            return new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);
        } catch (InvalidUserNameOrPasswordException err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/new-blog")
    public ResponseEntity<?> createBlog(@RequestBody AddBlogRequest createBlog) {
        try {
            var blogResponse = userService.createNewBlog(createBlog);
            return new ResponseEntity<>(blogResponse, HttpStatus.CREATED);
        } catch (BlogExistsException err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}
