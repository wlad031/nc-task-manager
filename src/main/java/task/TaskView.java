package task;

import mvc.View;

public abstract class TaskView implements View<TaskModel> {

    public abstract String read(TaskModel.Field field);
}
