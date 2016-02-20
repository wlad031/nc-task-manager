package dao;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

public class XmlDao implements Dao {

    private final String resourceName;
    private final Class objectType;
    private MarshallerAdapter marshallerAdapter;

    private List<Object> objectsList;

    public XmlDao(String resourceName, Class objectType) throws DaoException {

        if (objectType == null) {
            throw new DaoException("Object type can't be null");
        }

        if (!new File(XmlDao.class.getClassLoader().getResource(resourceName).getFile()).exists()) {
            throw new DaoException("File not found");
        }

        this.resourceName = resourceName;
        this.objectType = objectType;

        try {
            this.marshallerAdapter = new MarshallerAdapter(objectType);
        } catch (JAXBException e) {
            throw new DaoException("Error in JAXBContext creating", e);
        }

        this.objectsList = getAll();
    }

    /**
     * @param fieldName name of the field for search
     * @param value required value
     * @return the first object which has required value
     * @throws DaoException
     */
    @Override
    public Object get(String fieldName, Object value) throws DaoException {
        int index = getListIndex(fieldName, value);

        if (index == -1) {
            return null;
        } else {
            return objectsList.get(index);
        }
    }

    @Override
    public List getAll() throws DaoException {

        try (InputStream inputStream = XmlDaoFactory.class.getClassLoader().getResourceAsStream(resourceName)) {
            return marshallerAdapter.unmarshal(inputStream);
        } catch (JAXBException e) {
            throw new DaoException("Error in JAXB", e);
        } catch (IOException e) {
            throw new DaoException("Error in IO", e);
        }
    }

    @Override
    public void update(String fieldName, Object value, Object newObject) throws DaoException {

        try {
            objectType.cast(newObject);
        } catch (ClassCastException e) {
            throw new DaoException("Wrong object type", e);
        }

        objectsList.set(getListIndex(fieldName, value), newObject);
        marshal(objectsList);
    }

    @Override
    public void updateAll(List newList) throws DaoException {
        for (Object item : newList) {
            try {
                objectType.cast(item);
            } catch (ClassCastException e) {
                throw new DaoException("Wrong type of objects in the list");
            }
        }

        objectsList = newList;
        marshal(objectsList);
    }

    @Override
    public void remove(String fieldName, Object value) throws DaoException {
        objectsList.remove(getListIndex(fieldName, value));
        marshal(objectsList);
    }

    @Override
    public void removeAll() throws DaoException {
        objectsList.clear();
        marshal(objectsList);
    }

    @Override
    public void add(Object object) throws DaoException {

        try {
            objectType.cast(object);
        } catch (ClassCastException e) {
            throw new DaoException("Wrong object type", e);
        }

        objectsList.add(object);
        marshal(objectsList);
    }

    public int size() throws DaoException {
        return getAll().size();
    }

    private int getListIndex(String fieldName, Object value) throws DaoException {

        Field field;

        try {
            field = objectType.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new DaoException("Field not found", e);
        }

        try {
            field.getType().cast(value);

        } catch (ClassCastException e) {
            throw new DaoException("Value has wrong type", e);
        }

        for (int i = 0; i < objectsList.size(); i++) {

            field.setAccessible(true);

            try {
                if (field.get(objectsList.get(i)).equals(value)) {
                    return i;
                }
            } catch (IllegalAccessException e) {
                throw new DaoException("Error in getting value of the field", e);
            }
        }

        return -1;
    }

    private void marshal(List list) throws DaoException {

        try (OutputStream outputStream = new FileOutputStream(
                new File(XmlDaoFactory.class.getClassLoader().getResource(resourceName).getFile()))) {

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
