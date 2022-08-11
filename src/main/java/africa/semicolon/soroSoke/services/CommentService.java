package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Comment;
import africa.semicolon.soroSoke.dtos.requests.DeleteCommentRequest;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment saveComment(Comment newComment);

    Optional<Comment> deleteComment(DeleteCommentRequest commentToDelete);

    void deleteComment(String commentToDelete);

    int getNumberOfComments();

    List<Comment> getAllComments();
}
