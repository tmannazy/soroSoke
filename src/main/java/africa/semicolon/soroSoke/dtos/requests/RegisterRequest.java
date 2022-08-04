package africa.semicolon.soroSoke.dtos.requests;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
//    @NonNull
    private String password;
}
