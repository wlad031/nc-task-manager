package actions;

import controllers.TaskController;
import dao.exceptions.DaoException;
import models.TaskModel;

import java.util.Arrays;
import java.util.List;

public class TaskControllerActionNow extends TaskControllerAction<Integer> {
    public TaskControllerActionNow(TaskController controller, Integer... params) {
        super(controller, params);
    }


    @Override
    public void action() throws DaoException {
        List<TaskModel> models = dao.getAll();

        models.stream().filter(model -> model.isNow()).forEach(model -> view.show(Arrays.asList(model)));
    }
}
