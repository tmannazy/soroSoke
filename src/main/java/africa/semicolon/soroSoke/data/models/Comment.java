package africa.semicolon.soroSoke.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Comments")
@NoArgsConstructor
public class Comment {
    @Id
    private String id;
    private String comment;
    private String time;
}
