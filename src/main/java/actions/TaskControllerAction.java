package actions;

import controllers.TaskController;
import dao.Dao;
import dao.DaoException;
import models.TaskModel;
import views.TaskView;

import java.util.ArrayList;
import java.util.List;

public abstract class TaskControllerAction<T> implements Action {
    protected Dao dao;
    protected TaskView view;
    protected T[] params;

    public TaskControllerAction(TaskController controller, T... params) {
        this.dao = controller.getDao();
        this.view = controller.getView();
        this.params = params;
    }

    protected List<String> readTask() {
        List<String> res = new ArrayList<>();

        res.add(view.read(TaskModel.Field.TEXT));
        res.add(view.read(TaskModel.Field.DATE));

        return res;
    }

    protected int getLastId() throws DaoException {
        if (dao.size() == 0) {
            return 0;
        }

        return ((TaskModel) dao.getAll().get(dao.size() - 1)).getId();
    }
}
