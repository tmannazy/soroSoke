package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Atiku;
import africa.semicolon.soroSoke.data.repositories.AtikuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void deleteArticle(String articleToDelete) {
        for (var article : getAllArticles()) {
            if (article.getTitle().equalsIgnoreCase(articleToDelete)) {
                atikuRepository.delete(article);
                break;
            }
        }
    }
}
