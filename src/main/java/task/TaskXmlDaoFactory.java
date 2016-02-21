package task;

import dao.Dao;
import dao.DaoException;
import dao.ResourceDaoFactory;
import dao.XmlDao;
import settings.Settings;
import settings.SettingsException;

public class TaskXmlDaoFactory implements ResourceDaoFactory {

    @Override
    public Dao createDao() throws DaoException, SettingsException {
        return new XmlDao((String) Settings.getInstance().getSettingValue(Settings.Setting.MAIN_RESOURCE_NAME),
                TaskModel.class);
    }
}
