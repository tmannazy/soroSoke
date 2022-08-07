package africa.semicolon.soroSoke.dtos.requests;

import lombok.Data;
import lombok.NonNull;

@Data
public class RegisterRequest {
//    @NonNull
    private String email;
    private String password;

}
