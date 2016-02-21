package task;

import dao.Dao;
import dao.DaoException;
import mvc.Controller;
import mvc.ControllerException;
import mvc.View;
import settings.Settings;
import settings.SettingsException;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskController implements Controller<TaskModel> {

    private TaskManager taskManager;
    private TaskView view;

    public TaskController(TaskView view, Dao dao) throws ControllerException {
        this.view = view;
        this.taskManager = new TaskManager(dao);
    }

    public static final String dateFormatString = "MMMM d, yyyy";
    private static DateFormat dateFormat = new SimpleDateFormat(dateFormatString, Locale.ENGLISH);

    public static DateFormat getDateFormat() {
        return dateFormat;
    }

    @Override
    public void action(Action action, Object... params) throws ControllerException {

        try {

            switch (action) {
                case ADD:
                    add(params);
                    break;
                case UPDATE:
                    update(params);
                    break;
                case REMOVE:
                    remove(params);
                    break;
                case SHOW:
                    show(params);
                    break;
                case SHOW_ALL:
                    showAll(params);
                    break;
                default:
                    break;
            }

        } catch (ParseException e) {
            throw new ControllerException("Invalid date format", e);
        } catch (DaoException e) {
            throw new ControllerException("DAO error", e);
        }

    }

    private void show(Object... params) throws DaoException {
        TaskModel model = taskManager.getById((Integer) params[0]);

        List<TaskModel> list = new ArrayList<>();
        list.add(model);

        view.show(list);
    }

    private void showAll(Object... params) throws DaoException {
        List<TaskModel> list = taskManager.getAll();

        view.show(list);
    }

    private void add(Object... params) throws DaoException, ParseException {
        Integer id = getDao().size();

        String title = (String) view.read("Enter the title: ");
        String text = (String) view.read("Enter the text: ");
        String strDate = (String) view.read(
                "Enter the date (" + TaskController.dateFormatString + "): ");
        Date date = TaskController.getDateFormat().parse(strDate);

        taskManager.add(new TaskModel(id, title, text, date));
    }

    private void update(Object... params) throws ParseException, DaoException {
        TaskModel oldModel = taskManager.getById((Integer) params[0]);
        TaskModel newModel = new TaskModel(oldModel);

        if (params[1] != null) {
            newModel.setTitle((String) params[1]);
        }

        if (params[2] != null) {
            newModel.setText((String) params[2]);
        }

        if (params[3] != null) {
            newModel.setDate(dateFormat.parse((String) params[3]));
        }

        taskManager.update(oldModel, newModel);
    }

    public void remove(Object... params) throws DaoException {
        taskManager.remove((Integer) params[0]);
    }

    public Dao getDao() {
        return taskManager.getDao();
    }
}
