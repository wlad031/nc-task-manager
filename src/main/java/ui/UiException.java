package ui;

public class UiException extends Exception {
    public UiException() {
        super();
    }

    public UiException(String msg) {
        super(msg);
    }

    public UiException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
