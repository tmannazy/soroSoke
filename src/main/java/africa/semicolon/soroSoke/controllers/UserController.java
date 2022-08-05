package africa.semicolon.soroSoke.controllers;

import africa.semicolon.soroSoke.dtos.requests.RegisterRequest;
import africa.semicolon.soroSoke.exceptions.UserExistsException;
import africa.semicolon.soroSoke.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    public UserService userService;

    @PostMapping("/user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) throws UserExistsException {
        try {
            var serviceResponse = userService.registerUser(request);
            return new ResponseEntity<>(serviceResponse, HttpStatus.CREATED);
        } catch (UserExistsException err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    @PatchMapping("/user/newBlog")
//    public ResponseEntity<?> createBlog(@RequestBody AddBlogRequest createBlog) {
//        try{
//
//        }
//    }
}
