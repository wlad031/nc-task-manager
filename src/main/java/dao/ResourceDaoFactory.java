package dao;

import settings.SettingsException;

public interface ResourceDaoFactory {

    Dao createDao() throws DaoException, SettingsException;
}
