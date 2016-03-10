package actions;

import controllers.exceptions.ControllerException;
import dao.exceptions.DaoException;

import java.text.ParseException;

public interface Action {
    void action() throws ControllerException, DaoException, ParseException;
}
