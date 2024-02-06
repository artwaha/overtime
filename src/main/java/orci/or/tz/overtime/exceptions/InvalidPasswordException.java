package orci.or.tz.overtime.exceptions;

public class InvalidPasswordException extends Exception {
    private static final long serialVersionUID = 186345314369642486L;

    public InvalidPasswordException(String message) {
      super(message);
    }
}
