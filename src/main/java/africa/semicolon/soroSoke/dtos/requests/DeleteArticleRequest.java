package africa.semicolon.soroSoke.dtos.requests;

import lombok.Data;

@Data
public class DeleteArticleRequest {
    private String userName;
    private String articleId;
}
