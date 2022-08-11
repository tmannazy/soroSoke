package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Comment;
import africa.semicolon.soroSoke.data.repositories.CommentRepository;
import africa.semicolon.soroSoke.dtos.requests.DeleteCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment newComment) {
        return commentRepository.save(newComment);
    }

    @Override
    public Optional<Comment> deleteComment(DeleteCommentRequest commentToDelete) {
        var comment = commentRepository.findById(commentToDelete.getCommentId());
        commentRepository.deleteById(commentToDelete.getCommentId());
        return comment;
    }

    @Override
    public void deleteComment(String commentToDelete) {
        commentRepository.deleteById(commentToDelete);
    }

    @Override
    public int getNumberOfComments() {
        return (int) commentRepository.count();
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

}
