package actions;

import controllers.TaskController;
import dao.exceptions.DaoException;

public class TaskControllerActionRemove extends TaskControllerAction<Integer> {
    public TaskControllerActionRemove(TaskController controller, Integer... params) {
        super(controller, params);
    }

    @Override
    public void action() throws DaoException {
        if (params.length > 0) {
            int removedModelId = params[0];
            dao.remove(removedModelId);
        }
    }
}
