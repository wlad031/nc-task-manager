package dao.factories;

import dao.Dao;
import dao.exceptions.DaoException;
import settings.exceptions.SettingsException;

public interface ResourceDaoFactory {

    Dao createDao() throws DaoException, SettingsException;
}
