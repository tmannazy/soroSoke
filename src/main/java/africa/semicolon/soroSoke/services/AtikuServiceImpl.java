package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Atiku;
import africa.semicolon.soroSoke.data.models.Comment;
import africa.semicolon.soroSoke.data.repositories.AtikuRepository;
import africa.semicolon.soroSoke.dtos.requests.CommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AtikuServiceImpl implements AtikuService {
    @Autowired
    CommentService commentService;
    @Autowired
    private AtikuRepository atikuRepository;

    @Override
    public Atiku saveArticle(Atiku newAtiku) {
        return atikuRepository.save(newAtiku);
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
    public Atiku deleteArticle(String articleToDelete) {
        Atiku deletedArticle = null;
        for (var article : getAllArticles()) {
            if (Objects.equals(article.getId(), articleToDelete)) {
                atikuRepository.delete(article);
                deletedArticle = article;
                break;
            }
        }
        return deletedArticle;
    }

    @Override
    public Comment saveComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        var articleFound = atikuRepository.findById(commentRequest.getArticleId());
        if (articleFound.isPresent()) {
            comment.setComment(commentRequest.getCommentMessage());
            comment.setTime(commentRequest.getTime());
            commentService.saveComment(comment);
        }
        return comment;
    }
}
