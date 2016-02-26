package models;

import settings.Settings;
import settings.SettingsException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@XmlRootElement(name = "test")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskModel {

    @XmlElement
    protected Integer id;

    @XmlElement
    private String text;

    @XmlElement
    private Date date;

    @XmlElement
    private Boolean complete;

    private static DateFormat dateFormat;
    private static String stringDateFormat;

    public TaskModel() {

    }

    public TaskModel(int id, String text, Date date) {
        this(id, text, date, false);
    }

    public TaskModel(int id, String text, Date date, boolean complete) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.complete = complete;
    }

    public TaskModel(TaskModel that) {
        this.id = that.id;
        this.text = that.text;
        this.date = that.date;
        this.complete = that.complete;
    }

    static {
        try {
            stringDateFormat = (String) Settings.getInstance().getSettingValue(Settings.Setting.DATETIME_FORMAT);
        } catch (SettingsException e) {
            stringDateFormat = "dd.mm.yyyy HH:mm";
        }

        dateFormat = new SimpleDateFormat(stringDateFormat, Locale.ENGLISH);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isNow() {
        Date now = new Date();

        long diff = now.getTime() - date.getTime();
        long diffMinutes = diff / (60 * 1000) / 60;

        return diffMinutes == 0 && !isComplete();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskModel taskModel = (TaskModel) o;

        if (!id.equals(taskModel.id)) return false;
        if (text != null ? !text.equals(taskModel.text) : taskModel.text != null) return false;
        return !(date != null ? !date.equals(taskModel.date) : taskModel.date != null);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "id = " + id +
                ": " + text +
                ", notify - " + dateFormat.format(date) +
                ", done - " + complete;
    }

    public enum Field {
        TEXT,
        DATE
    }

    public static DateFormat getDateFormat() {
        return dateFormat;
    }

    public static String getStringDateFormat() {
        return stringDateFormat;
    }
}
