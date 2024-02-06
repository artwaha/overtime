package orci.or.tz.overtime.exceptions;

public class InActiveUserException extends Exception{
    private static final long serialVersionUID = 186345314369642486L;

    public InActiveUserException(String message) {
        super(message);
    }
}
