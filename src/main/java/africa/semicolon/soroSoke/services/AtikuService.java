package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Atiku;
import africa.semicolon.soroSoke.dtos.requests.CommentRequest;

import java.util.List;

public interface AtikuService {
    Atiku saveArticle(Atiku newAtiku);

    Atiku getArticleByTitle(String articleTitle);

    List<Atiku> getAllArticles();

    int getNumberOfArticles();

    void deleteComment(String commentToDel);

    void deleteArticle(String articleToDelete);

    Atiku saveComment(CommentRequest commentRequest);
}
