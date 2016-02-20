package task;

public class TaskException extends Exception {
    public TaskException() {
        super();
    }

    public TaskException(String msg) {
        super(msg);
    }

    public TaskException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
