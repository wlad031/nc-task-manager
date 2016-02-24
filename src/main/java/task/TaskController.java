package task;

import dao.Dao;
import dao.DaoException;
import mvc.Controller;
import mvc.ControllerException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller implementation for the task model
 */
public class TaskController implements Controller {

    private TaskManager taskManager;
    private TaskView view;

    public TaskController(TaskView view, Dao<TaskModel> dao) throws ControllerException {
        this.view = view;
        this.taskManager = new TaskManager(dao);
    }

    public static DateFormat getDateFormat() {
        return TaskModel.dateFormat;
    }

    @Override
    public <T> void action(Action action, T... params) throws ControllerException {

        try {
            switch (action) {
                case ADD:
                    add();
                    break;
                case UPDATE:
                    update(parseInt(params));
                    break;
                case REMOVE:
                    remove(parseInt(params));
                    break;
                case SHOW:
                    show(parseInt(params));
                    break;
                default:
                    break;
            }

        } catch (ParseException e) {
            throw new ControllerException("Invalid date format", e);
        } catch (DaoException e) {
            throw new ControllerException("Object not available", e);
        }
    }

    public void showNowTasks() throws DaoException {
        List<TaskModel> models = taskManager.getAll();

        for (TaskModel model : models) {
            if (model.isNow()) {
                view.show(Arrays.asList(model));
            }
        }
    }

    private void show(Integer... ids) throws DaoException {
        List<TaskModel> list;

        if (ids.length == 0) {
            list = taskManager.getAll();
        } else {
            list = new ArrayList<>();

            for (Integer i : ids) {
                TaskModel model = taskManager.getById(i);
                if (model != null) {
                    list.add(model);
                }
            }
        }

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

    private void update(Integer... ids) throws DaoException, ParseException {

        if (ids.length > 0) {
            TaskModel oldModel = taskManager.getById(ids[0]);
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
    }

    private void remove(Integer... ids) throws DaoException {
        if (ids.length > 0) {
            taskManager.remove(ids[0]);
        }
    }

    private List<String> readTask() {
        List<String> res = new ArrayList<>();

        res.add(view.read(TaskModel.Field.TITLE));
        res.add(view.read(TaskModel.Field.TEXT));
        res.add(view.read(TaskModel.Field.DATE));

        return res;
    }

    public Dao getDao() {
        return taskManager.getDao();
    }

    private int getLastId() throws DaoException {
        if (getDao().size() == 0) {
            return 0;
        }

        return ((TaskModel) getDao().getAll().get(getDao().size() - 1)).getId();
    }

    private <T> Integer[] parseInt(T... strings) {
        Integer[] res = new Integer[strings.length];

        for (int i = 0; i < strings.length; i++) {
            res[i] = Integer.parseInt((String) strings[i]);
        }

        return res;
    }
}
