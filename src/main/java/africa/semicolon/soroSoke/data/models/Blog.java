package africa.semicolon.soroSoke.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("Blog")
@NoArgsConstructor
public class Blog {
    @Id
    private String id;
    private String blogTitle;
    @DBRef
    private List<Atiku> articles = new ArrayList<>();
}
