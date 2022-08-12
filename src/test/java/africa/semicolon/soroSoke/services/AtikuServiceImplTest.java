package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Atiku;
import africa.semicolon.soroSoke.data.models.Comment;
import africa.semicolon.soroSoke.data.repositories.AtikuRepository;
import africa.semicolon.soroSoke.data.repositories.CommentRepository;
import africa.semicolon.soroSoke.dtos.requests.AtikuRequest;
import africa.semicolon.soroSoke.dtos.requests.CommentRequest;
import africa.semicolon.soroSoke.exceptions.ArticleRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AtikuServiceImplTest {

    @Autowired
    private AtikuRepository atikuRepository;

    @Autowired
    private AtikuService atikuService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @AfterEach
    void tearDown() {
        atikuRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    void testThatArticleCreated_sizeIsOne() {
        AtikuRequest atikuRequest = new AtikuRequest();
        Atiku newAtiku = new Atiku();
        atikuRequest.setTitle("Grit");
        atikuRequest.setBody("The power of the mind is the ability to stretch the thinking muscle.");
        atikuRequest.setUserName("Boyo");
        atikuRequest.setPassword("amanpia2023");

        newAtiku.setTime(LocalDateTime.now());
        newAtiku.setTitle(atikuRequest.getTitle());
        newAtiku.setBody(atikuRequest.getBody());
        atikuService.saveArticle(newAtiku);
        assertEquals(1L, atikuService.getNumberOfArticles());
    }

    @Test
    void testThatCommentCanBeAddedToArticle() {
        AtikuRequest atikuRequest = new AtikuRequest();
        Atiku newAtiku = new Atiku();
        atikuRequest.setTitle("Grit");
        newAtiku.setTime(LocalDateTime.now());
        newAtiku.setTitle(atikuRequest.getTitle());
        atikuService.saveArticle(newAtiku);

        CommentRequest commentRequest = new CommentRequest();
        Comment newComment = new Comment();
        commentRequest.setArticleId("GriT");
        commentRequest.setCommentMessage("It takes only GRIT to get over life challenges.");
        newComment.setCommentBody(commentRequest.getCommentMessage());
        newComment.setTime(LocalDateTime.now());
        atikuService.saveComment(commentRequest);
        var atikuFound = atikuService.getArticleByTitle(commentRequest.getArticleId());

        var savedComment = commentService.saveComment(newComment);
        atikuFound.getComments().add(savedComment);
        atikuRepository.save(atikuFound);
        assertEquals(1L, atikuService.getNumberOfArticles());
        assertEquals("It takes only GRIT to get over life challenges.",
                atikuService.getAllArticles().get(0).getComments().get(0).getCommentBody());
    }

    @Test
    void testToAddCommentsXAndY_toArticle_deleteX_sizeIsOne() {
        AtikuRequest atikuRequest = new AtikuRequest();
        Atiku newAtiku = new Atiku();
        atikuRequest.setTitle("Grit");
        newAtiku.setTime(LocalDateTime.now());
        newAtiku.setTitle(atikuRequest.getTitle());
        newAtiku.setBody("It only takes Persistence, Consistency, Repetition to achieve results in a given goal.");
        atikuService.saveArticle(newAtiku);

        CommentRequest commentRequest = new CommentRequest();
        Comment newComment = new Comment();
        commentRequest.setArticleId("GriT");
        commentRequest.setCommentMessage("It takes only GRIT to get over life challenges.");
        newComment.setCommentBody(commentRequest.getCommentMessage());
        newComment.setTime(LocalDateTime.now());
        var atikuFound = atikuService.getArticleByTitle(commentRequest.getArticleId());
        var savedComment = commentService.saveComment(newComment);
        atikuFound.getComments().add(savedComment);
        atikuService.saveArticle(atikuFound);

        CommentRequest commentRequest2 = new CommentRequest();
        Comment newComment2 = new Comment();
        commentRequest2.setArticleId("Grit");
        commentRequest2.setCommentMessage("God over everything.");
        newComment2.setCommentBody(commentRequest2.getCommentMessage());
        newComment2.setTime(LocalDateTime.now());
        var atikuFound2 = atikuService.getArticleByTitle(commentRequest2.getArticleId());
        var savedComment2 = commentService.saveComment(newComment2);
        atikuFound2.getComments().add(savedComment2);
        atikuService.saveArticle(atikuFound2);
        assertEquals(1L,atikuRepository.count());
        assertEquals(2L,commentService.getNumberOfComments());

        var commentToDel = atikuFound2.getComments().get(0).getId();
        atikuService.deleteComment(commentToDel);
        assertEquals(1L,commentService.getNumberOfComments());
        assertEquals(1L,atikuRepository.count());
    }

    @Test
    void testThatUserCanCreateBlogAndAddArticle() throws ArticleRequestException {
        Atiku newRequest = new Atiku();
        newRequest.setTitle("Welcome to Programming 101");
        newRequest.setBody("Here we are going to go on a journey to introduce you to the world of programming.");
        atikuService.saveArticle(newRequest);

        Atiku newRequest2 = new Atiku();
        newRequest2.setTitle("Welcome to Programming 101");
        newRequest2.setBody("Here we are going to go on a journey to introduce you to the world of programming.");
        atikuService.saveArticle(newRequest2);
    }

}