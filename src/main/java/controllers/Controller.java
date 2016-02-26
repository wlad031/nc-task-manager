package controllers;

import dao.DaoException;

/**
 * controllers.Controller interface
 */
public interface Controller<T> {

    void request(String actionName, T... params) throws ControllerException, DaoException;
}
