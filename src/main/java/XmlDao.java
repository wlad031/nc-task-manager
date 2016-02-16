import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class XmlDao implements Dao {

    private final String resourceName;
    private final Class objectType;
    private MarshallerAdapter marshallerAdapter;

    public XmlDao(String resourceName, Class objectType) throws DaoException {

        if (objectType == null) {
            throw new DaoException("Object type can't be null");
        }

        this.resourceName = resourceName;
        this.objectType = objectType;

        try {
            this.marshallerAdapter = new MarshallerAdapter(objectType);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object get(int id) {
        return getAll().get(0);
    }

    @Override
    public List getAll() {

        try (InputStream inputStream = XmlDaoFactory.class.getClassLoader().getResourceAsStream(resourceName);) {

            return marshallerAdapter.unmarshal(inputStream);

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(int id, Object object) throws DaoException {

        try {
            objectType.cast(object);
        } catch (ClassCastException e) {
            throw new DaoException("Wrong object type");
        }

        List list = getAll();

        if (id >= list.size()) {
            throw new DaoException("Wrong ID");
        }

        list.set(id, object);

        try (OutputStream outputStream = new FileOutputStream(
                new File(XmlDaoFactory.class.getClassLoader().getResource(resourceName).getFile()))) {

            marshallerAdapter.marshal(list, outputStream);

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void add(Object object) throws DaoException {

        try {
            objectType.cast(object);
        } catch (ClassCastException e) {
            throw new DaoException("Wrong object type");
        }

        List list = getAll();
        list.add(object);

        try (OutputStream outputStream = new FileOutputStream(
                new File(XmlDaoFactory.class.getClassLoader().getResource(resourceName).getFile()))) {

            marshallerAdapter.marshal(list, outputStream);

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
