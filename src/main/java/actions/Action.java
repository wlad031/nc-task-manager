package actions;

import controllers.ControllerException;
import dao.DaoException;

import java.text.ParseException;

public interface Action {
    void run() throws ControllerException, DaoException, ParseException;
}
