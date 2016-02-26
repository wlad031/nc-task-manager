package views;

import models.TaskModel;
import views.View;

public abstract class TaskView implements View<TaskModel> {

    public abstract String read(TaskModel.Field field);
}
