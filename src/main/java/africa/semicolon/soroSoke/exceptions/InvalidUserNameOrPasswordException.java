package africa.semicolon.soroSoke.exceptions;

public class InvalidUserNameOrPasswordException extends RuntimeException {
    public InvalidUserNameOrPasswordException(String message) {
        super(message);
    }
}
