package task;

import dao.Dao;
import dao.DaoException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "test")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskModel {

    @XmlElement
    private Integer id;

    @XmlElement
    private String title;

    @XmlElement
    private String text;

    @XmlElement
    private Date date;

    @XmlElement
    private Boolean finished;

    private static Dao dao;

    public static void setDao(Dao dao) {
        TaskModel.dao = dao;
    }

    public static TaskModel getModel(int id) throws TaskException {

        try {
            return (TaskModel) dao.get("id", id);
        } catch (DaoException e) {
            throw new TaskException("Task not found", e);
        }
    }

    public static List<TaskModel> getAllModels() throws TaskException {
        try {
            return dao.getAll();
        } catch (DaoException e) {
            throw new TaskException("Error in DAO", e);
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskModel taskModel = (TaskModel) o;

        if (!id.equals(taskModel.id)) return false;
        if (title != null ? !title.equals(taskModel.title) : taskModel.title != null) return false;
        if (text != null ? !text.equals(taskModel.text) : taskModel.text != null) return false;
        return !(date != null ? !date.equals(taskModel.date) : taskModel.date != null);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
