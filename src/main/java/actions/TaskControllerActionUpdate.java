package actions;

import controllers.TaskController;
import dao.DaoException;
import models.TaskModel;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class TaskControllerActionUpdate extends TaskControllerAction<Integer> {
    public TaskControllerActionUpdate(TaskController controller, Integer... params) {
        super(controller, params);
    }

    @Override
    public void run() throws DaoException, ParseException {
        if (params.length > 0) {

            TaskModel oldModel = (TaskModel) dao.get("id", params[0]);
            TaskModel newModel = new TaskModel(oldModel);

            if (params.length == 2 && params[1] == 1) {

                newModel.setComplete(true);

            } else {
                List<String> list = readTask();

                if (list.get(0).length() > 0) {
                    newModel.setText(list.get(0));
                }

                if (list.get(1).length() > 0) {
                    Date date = TaskModel.getDateFormat().parse(list.get(1));
                    newModel.setDate(date);
                }
            }

            dao.update("id", oldModel.getId(), newModel);
        }
    }
}
