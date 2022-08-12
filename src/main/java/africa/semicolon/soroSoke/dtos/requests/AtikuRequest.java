package africa.semicolon.soroSoke.dtos.requests;

import lombok.Data;

@Data
public class AtikuRequest {
    private String title;
    private String body;
    private String userName;
    private String password;
}
