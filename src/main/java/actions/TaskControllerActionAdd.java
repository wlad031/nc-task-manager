package actions;

import controllers.TaskController;
import controllers.exceptions.ControllerException;
import dao.exceptions.DaoException;
import models.TaskModel;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class TaskControllerActionAdd extends TaskControllerAction<Integer> {
    public TaskControllerActionAdd(TaskController controller, Integer... params) {
        super(controller, params);
    }

    @Override
    public void action() throws ControllerException, DaoException, ParseException {

        List<String> list = view.read();

        for (String string : list) {
            if (string.length() < 1) {
                throw new ControllerException("Parameters can not be empty");
            }
        }

        int id = getLastId() + 1;
        String text = list.get(0);
        Date date = TaskModel.getDateFormat().parse(list.get(1));

        dao.add(new TaskModel(id, text, date));
    }
}
