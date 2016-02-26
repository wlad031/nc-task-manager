package actions;

import controllers.ControllerException;
import controllers.TaskController;
import dao.DaoException;
import models.TaskModel;

import java.text.ParseException;
import java.util.List;

public class TaskControllerActionAdd extends TaskControllerAction<Integer> {
    public TaskControllerActionAdd(TaskController controller, Integer... params) {
        super(controller, params);
    }

    @Override
    public void run() throws ControllerException, DaoException, ParseException {
        Integer id = getLastId() + 1;

        List<String> list = readTask();

        for (String string : list) {
            if (string.length() < 1) {
                throw new ControllerException("Parameters can not be empty");
            }
        }

        dao.add(new TaskModel(id, list.get(0),
                TaskModel.getDateFormat().parse(list.get(1))));
    }
}
