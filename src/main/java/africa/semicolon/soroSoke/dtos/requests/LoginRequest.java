package africa.semicolon.soroSoke.dtos.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String password;
}
