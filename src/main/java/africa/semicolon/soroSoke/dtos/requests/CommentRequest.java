package africa.semicolon.soroSoke.dtos.requests;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CommentRequest {
    private final LocalDateTime now = LocalDateTime.now();
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Id
    private String id;
    private String userName;
    private String articleId;
    private String commentMessage;
    private String time = now.format(format);
}
