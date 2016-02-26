package dao;

import settings.Settings;
import settings.SettingsException;

public class SettingsDaoFactory implements ResourceDaoFactory {

    @Override
    public Dao createDao() throws DaoException, SettingsException {
        return new XmlDao(Settings.SETTINGS_FILE, Settings.SettingsItem.class);
    }
}
