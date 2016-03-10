package dao.factories;

import dao.Dao;
import dao.XmlDao;
import dao.exceptions.DaoException;
import models.TaskModel;
import settings.Settings;
import settings.exceptions.SettingsException;

public class TaskXmlDaoFactory implements ResourceDaoFactory {

    @Override
    public Dao createDao() throws DaoException, SettingsException {
        return new XmlDao((String) Settings.getInstance().getSettingValue(Settings.Setting.MAIN_RESOURCE_NAME),
                TaskModel.class);
    }
}
