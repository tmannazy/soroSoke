package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Atiku;
import africa.semicolon.soroSoke.data.repositories.AtikuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtikuServiceImpl implements AtikuService {

    @Autowired
    private AtikuRepository atikuRepository;

    @Autowired CommentService commentService;

    @Override
    public Atiku saveArticle(Atiku newAtiku) {return atikuRepository.save(newAtiku);}

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
}
