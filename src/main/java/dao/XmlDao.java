package dao;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * @param fieldName name of the field for search
     * @param value     required value
     * @return the first object which has required value
     * @throws DaoException
     */
    @Override
    public <E> T get(String fieldName, E value) throws DaoException {
        int index = getIndex(fieldName, value, 0);

        if (index == -1) {
            return null;
        } else {
            return list.get(index);
        }
    }

    /**
     * @return all objects from the list
     * @throws DaoException
     */
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

    /**
     * @param fieldName name of the field for search
     * @param value     required value
     * @return list of objects that have required value
     * @throws DaoException
     */
    @Override
    public List<T> getAll(String fieldName, T value) throws DaoException {
        List<T> res = new ArrayList<>();
        List<Integer> indices = getIndiсes(fieldName, value);

        for (Integer i : indices) {
            res.add(list.get(i));
        }

        return res;
    }

    /**
     * Replaces a necessary object on the new object
     *
     * @param fieldName name of the field for search
     * @param value     required value
     * @param newObject replacing object
     * @throws DaoException
     */
    @Override
    public <E> void update(String fieldName, E value, T newObject) throws DaoException {
        list.set(getIndex(fieldName, value, 0), newObject);
        marshal(list);
    }

    /**
     * Replaces all objects
     *
     * @param newList replacing list of the objects
     * @throws DaoException
     */
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
    public void add(List<T> objects) throws DaoException {
        for (T object : objects) {
            list.add(object);
        }

        marshal(list);
    }

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
                throw new DaoException("Error in getting value of the field", e);
            }
        }

        return -1;
    }

    private <E> List<Integer> getIndiсes(String fieldName, E value) throws DaoException {
        List<Integer> res = new ArrayList<>();

        int index = -1;

        while ((index = getIndex(fieldName, value, index + 1)) != -1) {
            res.add(index);
        }

        return res;
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
