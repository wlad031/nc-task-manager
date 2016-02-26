package actions;

import controllers.TaskController;
import dao.DaoException;

public class TaskControllerActionRemove extends TaskControllerAction<Integer> {
    public TaskControllerActionRemove(TaskController controller, Integer... params) {
        super(controller, params);
    }

    @Override
    public void run() throws DaoException {
        if (params.length > 0) {
            dao.remove("id", params[0]);
        }
    }
}
