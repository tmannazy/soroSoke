package africa.semicolon.soroSoke.dtos.requests;

import lombok.Data;

@Data
public class CommentRequest {
    private String userName;
    private String articleId;
    private String commentMessage;
}
