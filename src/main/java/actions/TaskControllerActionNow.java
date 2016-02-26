package actions;

import controllers.TaskController;
import dao.DaoException;
import models.TaskModel;

import java.util.Arrays;
import java.util.List;

public class TaskControllerActionNow extends TaskControllerAction<Integer> {
    public TaskControllerActionNow(TaskController controller, Integer... params) {
        super(controller, params);
    }


    @Override
    public void run() throws DaoException {
        List<TaskModel> models = dao.getAll();

        for (TaskModel model : models) {
            if (model.isNow()) {
                view.show(Arrays.asList(model));
            }
        }
    }
}
