package africa.semicolon.soroSoke.dtos.requests;

import lombok.Data;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class AtikuRequest {
//    @Nullable
//    private LocalDateTime now = LocalDateTime.now();
//    @Nullable
//    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private String title;
    private String body;
    private String userName;
    private String password;
//    @Getter
//    private String time = now.format(format);
    private String time;
}
