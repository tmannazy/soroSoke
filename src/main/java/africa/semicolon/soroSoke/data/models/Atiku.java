package africa.semicolon.soroSoke.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("Articles")
@NoArgsConstructor
public class Atiku {
    private final List<Comment> comments = new ArrayList<>();
}
