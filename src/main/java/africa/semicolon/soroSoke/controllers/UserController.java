package africa.semicolon.soroSoke.controllers;

import africa.semicolon.soroSoke.dtos.requests.*;
import africa.semicolon.soroSoke.exceptions.*;
import africa.semicolon.soroSoke.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/user/login")
    public ResponseEntity<?> accountLogin(@RequestBody LoginRequest loginRequest) {
        try {
            var loginResponse = userService.userLogin(loginRequest);
            return new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);
        } catch (InvalidUserNameOrPasswordException err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/user/new-blog")
    public ResponseEntity<?> createBlog(@RequestBody AddBlogRequest createBlog) {
        try {
            var blogResponse = userService.createNewBlog(createBlog);
            return new ResponseEntity<>(blogResponse, HttpStatus.CREATED);
        } catch (BlogExistsException err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}
