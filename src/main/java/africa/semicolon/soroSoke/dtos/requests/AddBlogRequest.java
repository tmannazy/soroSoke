package africa.semicolon.soroSoke.dtos.requests;

import lombok.Data;

@Data
public class AddBlogRequest {
    private String userName;
    private String blogTitle;
    private String password;
}
