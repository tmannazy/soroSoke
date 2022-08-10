package africa.semicolon.soroSoke.dtos.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class AtikuRequest {
    @Nullable
    private final LocalDateTime now = LocalDateTime.now();
    @Nullable
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    @Id
    private String id;
    private String title;
    private String body;
    private String userName;
    @Getter
    private String time = now.format(format);

}
