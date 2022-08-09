package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Comment;

public interface CommentService {
    Comment saveComment(Comment newComment);

    void deleteComment(String commentToDelete);

    int getNumberOfComments();
}
