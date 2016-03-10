package controllers;

import actions.*;
import controllers.exceptions.ControllerException;
import dao.Dao;
import dao.exceptions.DaoException;
import models.TaskModel;
import views.TaskView;

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
            ControllerActions.valueOf(actionName.toUpperCase()).callAction(this, params);
        } catch (IllegalArgumentException e) {
            throw new ControllerException("Wrong action", e);
        } catch (DaoException e) {
            throw new ControllerException("Object not available", e);
        } catch (ParseException e) {
            throw new ControllerException("Invalid datetime format", e);
        }
    }

    private enum ControllerActions {

        ADD {
            @Override
            public void callAction(TaskController controller, Integer... params)
                    throws ParseException, DaoException, ControllerException {

                new TaskControllerActionAdd(controller, params).action();
            }
        },

        COMPLETE {
            @Override
            public void callAction(TaskController controller, Integer... params)
                    throws ParseException, DaoException {

                new TaskControllerActionComplete(controller, params).action();
            }
        },

        NOW {
            @Override
            public void callAction(TaskController controller, Integer... params)
                    throws ParseException, DaoException {

                new TaskControllerActionNow(controller, params).action();
            }
        },

        REMOVE {
            @Override
            public void callAction(TaskController controller, Integer... params)
                    throws ParseException, DaoException {

                new TaskControllerActionRemove(controller, params).action();
            }
        },

        SHOW {
            @Override
            public void callAction(TaskController controller, Integer... params)
                    throws ParseException, DaoException {

                new TaskControllerActionShow(controller, params).action();
            }
        },

        UPDATE {
            @Override
            public void callAction(TaskController controller, Integer... params)
                    throws ParseException, DaoException {

                new TaskControllerActionUpdate(controller, params).action();
            }
        };

        public abstract void callAction(TaskController controller, Integer... params)
                throws ParseException, DaoException, ControllerException;
    }

    public Dao getDao() {
        return this.dao;
    }

    public TaskView getView() {
        return view;
    }
}
