package views;

import models.TaskModel;

import java.util.List;

public abstract class TaskView implements View<TaskModel> {
    public abstract List<String> read();
}
