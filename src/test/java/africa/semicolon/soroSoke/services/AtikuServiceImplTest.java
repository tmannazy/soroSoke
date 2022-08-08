package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Atiku;
import africa.semicolon.soroSoke.data.models.Comment;
import africa.semicolon.soroSoke.data.repositories.AtikuRepository;
import africa.semicolon.soroSoke.dtos.requests.AtikuRequest;
import africa.semicolon.soroSoke.dtos.requests.CommentRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AtikuServiceImplTest {

    @Autowired
    private AtikuRepository atikuRepository;

    @AfterEach
    void tearDown() {
        atikuRepository.deleteAll();
    }

    @Test
    void testThatArticleCreated_sizeIsOne() {
        AtikuRequest atikuRequest = new AtikuRequest();
        Atiku newAtiku = new Atiku();
        atikuRequest.setTitle("Grit");
        newAtiku.setTime(atikuRequest.getTime());
        newAtiku.setTitle(atikuRequest.getTitle());
        atikuRepository.save(newAtiku);
        System.out.println(newAtiku);
        assertEquals(1L, atikuRepository.count());
    }

    @Test
    void testThatCommentCanBeAddedToArticle() {
        AtikuRequest atikuRequest = new AtikuRequest();
        Atiku newAtiku = new Atiku();
        atikuRequest.setTitle("Grit");
        newAtiku.setTime(atikuRequest.getTime());
        newAtiku.setTitle(atikuRequest.getTitle());
        atikuRepository.save(newAtiku);

        CommentRequest commentRequest = new CommentRequest();
        Comment newComment = new Comment();
        commentRequest.setArticleTitle("GriT");
        commentRequest.setCommentMessage("It takes only GRIT to get over life challenges.");
        newComment.setArticleTitle(commentRequest.getArticleTitle());
        newComment.setComment(commentRequest.getCommentMessage());
        newComment.setId("122");
        newComment.setTime(commentRequest.getTime());
        var atikuFound = atikuRepository.getAtikuByTitleIgnoreCase(commentRequest.getArticleTitle());
        atikuFound.getComments().add(newComment);
        atikuRepository.save(atikuFound);
        assertEquals(1L, atikuRepository.count());
        assertEquals("It takes only GRIT to get over life challenges.",
                atikuRepository.findAll().get(0).getComments().get(0).getComment());
    }


}