package africa.semicolon.soroSoke.dtos.responses;

import africa.semicolon.soroSoke.data.models.Atiku;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserBlogResponse {
    private String id;
    private String blogTitle;
    private List<Atiku> articles = new ArrayList<>();
}
