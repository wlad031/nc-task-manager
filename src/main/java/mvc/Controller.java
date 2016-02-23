package mvc;

import dao.DaoException;

/**
 * Controller interface
 */
public interface Controller {

    <T> void action(Action action, T... params) throws ControllerException, DaoException;

    /**
     * Available actions
     */
    enum Action {
        ADD,
        UPDATE,
        REMOVE,
        SHOW
    }
}
