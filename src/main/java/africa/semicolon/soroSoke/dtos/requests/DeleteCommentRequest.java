package africa.semicolon.soroSoke.dtos.requests;

import lombok.Data;

@Data
public class DeleteCommentRequest {
    private String commentId;
    private String userName;
    private String password;
}
