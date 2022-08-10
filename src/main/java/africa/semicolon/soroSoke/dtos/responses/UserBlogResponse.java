package africa.semicolon.soroSoke.dtos.responses;

import africa.semicolon.soroSoke.data.models.Atiku;

import java.util.ArrayList;
import java.util.List;

public class AllBlogResponse {
    private String id;
    private String blogTitle;
    private List<Atiku> articles = new ArrayList<>();
}
