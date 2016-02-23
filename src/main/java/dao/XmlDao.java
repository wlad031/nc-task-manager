package dao;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

/**
 * DAO implementation which uses XML files
 *
 * @param <T> type of read/wrote objects
 */
public class XmlDao<T> implements Dao<T> {

    private final String resourceName;
    private MarshallerAdapter<T> marshallerAdapter;

    private Class<T> clazz;
    private List<T> list;

    public XmlDao(String resourceName, Class<T> clazz) throws DaoException {

        if (!new File(XmlDao.class.getClassLoader().getResource(resourceName).getFile()).exists()) {
            throw new DaoException("File not found");
        }

        this.resourceName = resourceName;
        this.clazz = clazz;

        try {
            this.marshallerAdapter = new MarshallerAdapter(clazz);
        } catch (JAXBException e) {
            throw new DaoException("Error in JAXBContext creating", e);
        }

        this.list = getAll();
    }

    @Override
    public <E> T get(String fieldName, E value) throws DaoException {
        return list.get(getIndex(fieldName, value, 0));
    }

    @Override
    public List<T> getAll() throws DaoException {

        try (InputStream inputStream = XmlDao.class.getClassLoader().getResourceAsStream(resourceName)) {
            return marshallerAdapter.unmarshal(inputStream);
        } catch (JAXBException e) {
            throw new DaoException("Error in JAXB", e);
        } catch (IOException e) {
            throw new DaoException("Error in IO", e);
        }
    }

    @Override
    public <E> void update(String fieldName, E value, T newObject) throws DaoException {
        list.set(getIndex(fieldName, value, 0), newObject);
        marshal(list);
    }

    @Override
    public void updateAll(List<T> newList) throws DaoException {
        list = newList;
        marshal(list);
    }

    @Override
    public <E> void remove(String fieldName, E value) throws DaoException {
        list.remove(getIndex(fieldName, value, 0));
        marshal(list);
    }

    @Override
    public void clear() throws DaoException {
        list.clear();
        marshal(list);
    }

    @Override
    public void add(T object) throws DaoException {
        list.add(object);
        marshal(list);
    }

    @Override
    public int size() {
        return list.size();
    }

    private <E> int getIndex(String fieldName, E value, int fromIndex) throws DaoException {

        Field field;

        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new DaoException("Field not found", e);
        }

        try {
            field.getType().cast(value);
        } catch (ClassCastException e) {
            throw new DaoException("Value has wrong type", e);
        }

        for (int i = fromIndex; i < list.size(); i++) {

            field.setAccessible(true);

            try {
                if (field.get(list.get(i)).equals(value)) {
                    return i;
                }
            } catch (IllegalAccessException e) {
                throw new DaoException("Error in getting value", e);
            }
        }

        throw new DaoException("Object not found");
    }

    private void marshal(List<T> list) throws DaoException {

        try (OutputStream outputStream = new FileOutputStream(
                new File(XmlDao.class.getClassLoader().getResource(resourceName).getFile()))) {

            marshallerAdapter.marshal(list, outputStream);

        } catch (JAXBException e) {
            throw new DaoException("Error in JAXB", e);
        } catch (FileNotFoundException e) {
            throw new DaoException("File not found", e);
        } catch (IOException e) {
            throw new DaoException("Error in IO", e);
        }
    }
}
