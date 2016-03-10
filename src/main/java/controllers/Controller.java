package controllers;

import controllers.exceptions.ControllerException;
import dao.exceptions.DaoException;

/**
 * controllers.Controller interface
 */
public interface Controller<T> {

    void request(String actionName, T... params) throws ControllerException, DaoException;
}
