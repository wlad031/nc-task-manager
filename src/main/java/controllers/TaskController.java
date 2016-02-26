package controllers;

import actions.TaskControllerAction;
import dao.Dao;
import dao.DaoException;
import models.TaskModel;
import utils.StringUtils;
import views.TaskView;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

/**
 * controllers.Controller implementation for the task model
 */
public class TaskController implements Controller<Integer> {

    private Dao dao;
    private TaskView view;

    public TaskController(TaskView view, Dao<TaskModel> dao) throws ControllerException {
        this.view = view;
        this.dao = dao;
    }

    @Override
    public void request(String actionName, Integer... params) throws ControllerException {

        try {
            actionName = StringUtils.toNormalCase(actionName);
            String actionClassName = TaskControllerAction.class.getName() + actionName;
            Class<? extends TaskControllerAction> actionClass =
                    (Class<? extends TaskControllerAction>) Class.forName(actionClassName);

            TaskControllerAction action =
                    actionClass.getConstructor(TaskController.class, Integer[].class).newInstance(this, params);
            action.run();

        } catch (DaoException e) {
            throw new ControllerException("Object not available", e);
        } catch (ClassNotFoundException | ClassCastException |
                NoSuchMethodException | IllegalAccessException |
                InstantiationException | InvocationTargetException e) {

            throw new ControllerException("Wrong action", e);

        } catch (ParseException e) {
            throw new ControllerException("Invalid datetime format", e);
        }
    }

    public Dao getDao() {
        return this.dao;
    }

    public TaskView getView() {
        return view;
    }
}
