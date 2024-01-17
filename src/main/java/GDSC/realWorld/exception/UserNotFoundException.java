package GDSC.realWorld.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("UserNotFound");
    }
}
