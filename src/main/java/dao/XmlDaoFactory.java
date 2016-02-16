package dao;

import java.io.File;

public class XmlDaoFactory implements FileDaoFactory {

    @Override
    public Dao createDao(File file, Class objectType) {
        return new XmlDao(file, objectType);
    }
}
