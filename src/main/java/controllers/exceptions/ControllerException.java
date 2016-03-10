package controllers.exceptions;

public class ControllerException extends Exception {
    public ControllerException() {
        super();
    }

    public ControllerException(String msg) {
        super(msg);
    }

    public ControllerException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
