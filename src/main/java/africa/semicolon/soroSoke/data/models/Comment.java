package africa.semicolon.soroSoke.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("Comments")
@NoArgsConstructor
public class Comment {
    @Id
    private String id;
    private String commentBody;
    private LocalDateTime time;
}
