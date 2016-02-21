package mvc;

import dao.DaoException;

public interface Controller<T> {
    void action(Action action, Object... params) throws ControllerException, DaoException;

    enum Action {
        ADD,
        UPDATE,
        REMOVE,
        SHOW,
        SHOW_ALL
    }
}
