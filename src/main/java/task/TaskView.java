package task;

import mvc.View;

public abstract class TaskView implements View<TaskModel> {

    public enum Field {
        TITLE,
        TEXT,
        DATE
    }

    public abstract String read(TaskView.Field field);
}
