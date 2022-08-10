package africa.semicolon.soroSoke.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("Articles")
@NoArgsConstructor
public class Atiku {
    @Id
    private String id;
    private String title;
    private String body;
    private String time;
    @DBRef
    private List<Comment> comments = new ArrayList<>();
}
