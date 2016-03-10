package actions;

import controllers.TaskController;
import dao.Dao;
import dao.exceptions.DaoException;
import models.TaskModel;
import views.TaskView;

public abstract class TaskControllerAction<T> implements Action {
    protected Dao dao;
    protected TaskView view;
    protected T[] params;

    public TaskControllerAction(TaskController controller, T... params) {
        this.dao = controller.getDao();
        this.view = controller.getView();
        this.params = params;
    }

    protected int getLastId() throws DaoException {
        if (dao.size() == 0) {
            return 0;
        }

        return ((TaskModel) dao.getAll().get(dao.size() - 1)).getId();
    }
}
