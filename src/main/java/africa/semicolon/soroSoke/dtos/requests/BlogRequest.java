package africa.semicolon.soroSoke.dtos.requests;

import lombok.Data;

@Data
public class BlogRequest {
    private String userName;
    private String blogTitle;
    private String password;
}
