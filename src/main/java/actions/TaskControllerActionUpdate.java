package actions;

import controllers.TaskController;
import dao.exceptions.DaoException;
import models.TaskModel;

import java.text.ParseException;
import java.util.List;

public class TaskControllerActionUpdate extends TaskControllerAction<Integer> {
    public TaskControllerActionUpdate(TaskController controller, Integer... params) {
        super(controller, params);
    }

    @Override
    public void action() throws DaoException, ParseException {

        if (params.length > 0) {

            int updatedModelId = params[0];

            TaskModel oldModel = (TaskModel) dao.get(updatedModelId);
            TaskModel newModel = new TaskModel(oldModel);

            List<String> list = view.read();

            String newText = list.get(0);
            String newDate = list.get(1);

            if (newText.length() > 0) {
                newModel.setText(newText);
            }

            if (newDate.length() > 0) {
                newModel.setDate(TaskModel.getDateFormat().parse(newDate));
            }

            dao.update(updatedModelId, newModel);
        }
    }
}
