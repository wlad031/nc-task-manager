package task;

import dao.Dao;
import dao.DaoException;
import mvc.Controller;
import mvc.ControllerException;

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

    public TaskController(TaskView view, Dao<TaskModel> dao) throws ControllerException {
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
                    add();
                    break;
                case UPDATE:
                    update((Integer) params[0]);
                    break;
                case REMOVE:
                    remove((Integer) params[0]);
                    break;
                case SHOW:
                    show((Integer) params[0]);
                    break;
                case SHOW_ALL:
                    showAll();
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

    private void show(Integer id) throws DaoException {
        TaskModel model = taskManager.getById(id);

        List<TaskModel> list = new ArrayList<>();
        list.add(model);

        view.show(list);
    }

    private void showAll() throws DaoException {
        List<TaskModel> list = taskManager.getAll();
        view.show(list);
    }

    private void add() throws DaoException, ParseException, ControllerException {
        Integer id = getLastId() + 1;

        List<String> list = readTask();

        for (String string : list) {
            if (string.length() < 1) {
                throw new ControllerException("Parameters can not be empty");
            }
        }

        taskManager.add(new TaskModel(id, list.get(0), list.get(1),
                TaskController.getDateFormat().parse(list.get(2))));
    }

    private void update(Integer id) throws DaoException, ParseException {
        TaskModel oldModel = taskManager.getById(id);
        TaskModel newModel = new TaskModel(oldModel);

        List<String> list = readTask();

        if (list.get(0).length() > 0) {
            newModel.setTitle(list.get(0));
        }

        if (list.get(1).length() > 0) {
            newModel.setText(list.get(1));
        }

        if (list.get(2).length() > 0) {
            Date date = TaskController.getDateFormat().parse(list.get(2));
            newModel.setDate(date);
        }

        taskManager.update(oldModel, newModel);
    }

    private void remove(Integer id) throws DaoException {
        taskManager.remove(id);
    }

    private List<String> readTask() {
        List<String> res = new ArrayList<>();

        res.add((String) view.read("Enter the title: "));
        res.add((String) view.read("Enter the text: "));
        res.add((String) view.read("Enter the date (" + TaskController.dateFormatString + "): "));

        return res;
    }

    public Dao getDao() {
        return taskManager.getDao();
    }

    private int getLastId() throws DaoException {
        return ((TaskModel) getDao().getAll().get(getDao().size() - 1)).getId();
    }
}
