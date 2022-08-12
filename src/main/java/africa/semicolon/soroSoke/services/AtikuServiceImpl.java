package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Atiku;
import africa.semicolon.soroSoke.data.models.Comment;
import africa.semicolon.soroSoke.data.models.User;
import africa.semicolon.soroSoke.data.repositories.AtikuRepository;
import africa.semicolon.soroSoke.dtos.requests.CommentRequest;
import africa.semicolon.soroSoke.dtos.requests.DeleteCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AtikuServiceImpl implements AtikuService {
    @Autowired
    CommentService commentService;
    Math.PI
    @Autowired
    private AtikuRepository atikuRepository;

    @Override
    public Atiku saveArticle(Atiku newAtiku) {
        duplicateArticleDoesNotExist(String.valueOf(newAtiku));
        var savedArticle = atikuRepository.save(newAtiku);

        System.out.println(newAtiku);
        return savedArticle;
    }

    @Override
    public Atiku getArticleByTitle(String articleTitle) {
        return atikuRepository.getAtikuByTitleIgnoreCase(articleTitle);
    }

    @Override
    public List<Atiku> getAllArticles() {
        return atikuRepository.findAll();
    }

    @Override
    public int getNumberOfArticles() {
        return (int) atikuRepository.count();
    }

    @Override
    public void deleteComment(String commentToDel) {
        commentService.deleteComment(commentToDel);
    }

    @Override
    public Optional<Comment> deleteComment(DeleteCommentRequest commentToDel) {
        return commentService.deleteComment(commentToDel);
    }

    @Override
    public Atiku deleteArticle(String articleToDelete) {
        Atiku deletedArticle = null;
        for (var article : getAllArticles()) {
            if (Objects.equals(article.getId(), articleToDelete)) {
                deletedArticle = article;
                atikuRepository.delete(article);
            }
        }
        return deletedArticle;
    }

    @Override
    public Comment saveComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        var articlesFound = atikuRepository.findAll();
        for (var article : articlesFound) {
            if (Objects.equals(article.getId(), commentRequest.getArticleId())) {
                mapComment(commentRequest, comment, article);
            }
        }
        return comment;
    }

    private void mapComment(CommentRequest commentRequest, Comment comment, Atiku article) {
        comment.setCommentBody(commentRequest.getCommentMessage());
        comment.setTime(LocalDateTime.now());
        var savedComment = commentService.saveComment(comment);
        article.getComments().add(savedComment);
        atikuRepository.save(article);
    }
    private boolean duplicateArticleDoesNotExist(String userName){
        User user = new User();
        return userName.equals(user.getUserName());
    }
}
