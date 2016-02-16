package dao;

import java.io.File;

public class XmlDao implements Dao {

    private File file;
    private Class objectType;

    public XmlDao(File file, Class objectType) {
        this.file = file;
        this.objectType = objectType;
    }

    @Override
    public Object get(int id) {
        return null;
    }

    @Override
    public Object[] getAll() {
        return new Object[0];
    }

    @Override
    public void update(int id, Object object) {

    }

    @Override
    public void create(Object object) {

    }

    @Override
    public void remove(int id) {

    }
}
