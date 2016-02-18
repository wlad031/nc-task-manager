import java.util.Date;
import java.util.List;

public class TaskModel {
    private int id;
    private String title;
    private String text;
    private Date date;
    private boolean finished;

    public static TaskModel getModel(int id) {

        return null;
    }

    public static List<TaskModel> getAllModels() {

        return null;
    }

    public TaskModel() {
        this(0, "Test task title", "Test task text", new Date());
    }

    public TaskModel(int id, String title, String text, Date date) {
        this(id, title, text, date, false);
    }

    public TaskModel(int id, String title, String text, Date date, boolean finished) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
