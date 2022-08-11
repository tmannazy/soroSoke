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

    @PostMapping("/user/article")
    public ResponseEntity<?> createArticle(@RequestBody AtikuRequest request) {
        try {
            var articleResponse = userService.addArticle(request);
            return new ResponseEntity<>(articleResponse, HttpStatus.CREATED);
        } catch (ArticleRequestException err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("user/{email}")
    public ResponseEntity<?> viewBlog(@PathVariable String email) {
        try {
            var viewBlog = userService.displayUserBlog(email);
            return new ResponseEntity<>(viewBlog, HttpStatus.ACCEPTED);
        } catch (BlogExistsException err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("user/comment")
    public ResponseEntity<?> comment(@RequestBody CommentRequest commentRequest) {
        try {
            var comment = userService.addComment(commentRequest);
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        } catch (CommentRequestException err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<?> delete(@RequestBody DeleteArticleRequest deleteArticleRequest) {
        try {
            var delete = userService.deleteArticle(deleteArticleRequest);
            return new ResponseEntity<>(delete, HttpStatus.ACCEPTED);
        } catch (ArticleRequestException err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
