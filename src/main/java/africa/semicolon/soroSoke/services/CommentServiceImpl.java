package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Comment;
import africa.semicolon.soroSoke.data.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment newComment) {
        return commentRepository.save(newComment);
    }

    @Override
    public void deleteComment(String commentToDelete) {
        commentRepository.deleteById(commentToDelete);
    }

    @Override
    public int getNumberOfComments() {
        return (int) commentRepository.count();
    }

}
