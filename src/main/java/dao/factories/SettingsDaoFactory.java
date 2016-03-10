package dao.factories;

import dao.Dao;
import dao.XmlDao;
import dao.exceptions.DaoException;
import settings.Settings;
import settings.exceptions.SettingsException;

public class SettingsDaoFactory implements ResourceDaoFactory {

    @Override
    public Dao createDao() throws DaoException, SettingsException {
        return new XmlDao(Settings.SETTINGS_FILE, Settings.SettingsItem.class);
    }
}
