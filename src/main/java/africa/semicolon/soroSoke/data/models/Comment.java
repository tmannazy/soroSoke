package africa.semicolon.soroSoke.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Comment {
    @Id
    private String id;
    private String comment;
    private String time;
    private String articleTitle;
}
