package task;

import dao.Dao;
import dao.DaoException;

import java.util.List;

public class TaskManager {

    private Dao dao;

    public Dao getDao() {
        return dao;
    }

    public TaskManager(Dao dao) {
        this.dao = dao;
    }

    public void add(TaskModel model) throws DaoException {
        dao.add(model);
    }

    public void update(TaskModel oldModel, TaskModel newModel) throws DaoException {
        dao.update("id", oldModel.getId(), newModel);
    }

    public void remove(int id) throws DaoException {
        dao.remove("id", id);
    }

    public TaskModel getById(int id) throws DaoException {
        return (TaskModel) dao.get("id", id);
    }

    public List<TaskModel> getAll() throws DaoException {
        return dao.getAll();
    }
}

