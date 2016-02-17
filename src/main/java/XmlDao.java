import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.List;

public class XmlDao implements Dao {

    private final String resourceName;
    private final Class objectType;
    private MarshallerAdapter marshallerAdapter;

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
    }

    @Override
    public Object get(int id) throws DaoException {

        List list = getAll();

        if (id >= list.size() || id < 0) {
            throw new DaoException("Wrong ID");
        }

        return list.get(id);
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
    public void update(int id, Object object) throws DaoException {

        try {
            objectType.cast(object);
        } catch (ClassCastException e) {
            throw new DaoException("Wrong object type", e);
        }

        List list = getAll();

        if (id >= list.size() || id < 0) {
            throw new DaoException("Wrong ID");
        }

        list.set(id, object);

        marshal(list);
    }

    @Override
    public void remove(int id) throws DaoException {

        List list = getAll();

        if (id >= list.size() || id < 0) {
            throw new DaoException("Wrong ID");
        }

        list.remove(id);

        marshal(list);
    }

    @Override
    public void add(Object object) throws DaoException {

        try {
            objectType.cast(object);
        } catch (ClassCastException e) {
            throw new DaoException("Wrong object type", e);
        }

        List list = getAll();
        list.add(object);

        marshal(list);
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
