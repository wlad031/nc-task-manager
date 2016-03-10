package actions;

import controllers.TaskController;
import dao.exceptions.DaoException;
import models.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class TaskControllerActionShow extends TaskControllerAction<Integer> {
    public TaskControllerActionShow(TaskController controller, Integer... params) {
        super(controller, params);
    }

    @Override
    public void action() throws DaoException {
        List<TaskModel> list;

        if (params.length == 0) {
            list = dao.getAll();
        } else {
            list = new ArrayList<>();

            for (Integer i : params) {
                TaskModel model = (TaskModel) dao.get(i);
                if (model != null) {
                    list.add(model);
                }
            }
        }

        view.show(list);
    }
}
