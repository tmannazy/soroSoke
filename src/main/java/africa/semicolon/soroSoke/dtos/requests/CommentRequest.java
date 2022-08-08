package africa.semicolon.soroSoke.dtos.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentRequest {
    private final LocalDateTime now = LocalDateTime.now();
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Id
    private String id;
    @Getter
    @Setter
    private String articleTitle;
    @Setter
    @Getter
    private String commentMessage;
    @Getter
    private String time = now.format(format);
}
