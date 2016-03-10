package dao;

import dao.exceptions.DaoException;
import models.Model;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.List;

/**
 * DAO implementation which uses XML files
 *
 * @param <T> type of read/wrote objects
 */
public class XmlDao<T extends Model> implements Dao<T> {

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
    public T get(int id) throws DaoException {
        return list.get(getIndex(id, 0));
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
    public void update(int id, T newObject) throws DaoException {
        list.set(getIndex(id, 0), newObject);
        marshal(list);
    }

    @Override
    public void updateAll(List<T> newList) throws DaoException {
        list = newList;
        marshal(list);
    }

    @Override
    public void remove(int id) throws DaoException {
        list.remove(getIndex(id, 0));
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

    private int getIndex(int id, int offset) throws DaoException {

        for (int i = offset; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                return i;
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
