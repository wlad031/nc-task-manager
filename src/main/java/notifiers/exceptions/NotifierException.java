package notifiers.exceptions;

public class NotifierException extends Exception {
    public NotifierException() {
        super();
    }

    public NotifierException(String msg) {
        super(msg);
    }

    public NotifierException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
