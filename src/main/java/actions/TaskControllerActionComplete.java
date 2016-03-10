package actions;

import controllers.TaskController;
import dao.exceptions.DaoException;
import models.TaskModel;

import java.text.ParseException;

public class TaskControllerActionComplete extends TaskControllerAction<Integer> {
    public TaskControllerActionComplete(TaskController controller, Integer... params) {
        super(controller, params);
    }

    @Override
    public void action() throws DaoException, ParseException {

        if (params.length > 0) {
            int updatedModelId = params[0];

            TaskModel oldModel = (TaskModel) dao.get(updatedModelId);
            TaskModel newModel = new TaskModel(oldModel);

            newModel.setComplete(true);

            dao.update(updatedModelId, newModel);
        }
    }
}
