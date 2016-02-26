package dao;

import dao.Wrapper;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for simple marshalling/unmarshalling lists of objects
 *
 * @param <T> type of the (un)marshaled objects
 */
public class MarshallerAdapter<T> {

    private JAXBContext context;

    /**
     * Creates JAXB context
     *
     * @param clazz class of objects
     * @throws JAXBException
     */
    public MarshallerAdapter(Class<T> clazz) throws JAXBException {
        context = JAXBContext.newInstance(Wrapper.class, clazz);
    }

    /**
     * Unmarshals objects
     *
     * @param inputStream stream from which the unmarshalling
     * @return list of the unmarshaled objects
     */
    public List<T> unmarshal(InputStream inputStream) throws JAXBException {

        Unmarshaller unmarshaller = context.createUnmarshaller();
        StreamSource xml = new StreamSource(inputStream);

        try {
            Wrapper<T> wrapper = (Wrapper<T>) unmarshaller.unmarshal(xml, Wrapper.class).getValue();

            return wrapper.getList();

        } catch (UnmarshalException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Marshals the list of objects
     *
     * @param list         list of marshaled objects
     * @param outputStream stream to which the marshalling
     */
    public void marshal(List<T> list, OutputStream outputStream) throws JAXBException {

        Wrapper<T> wrapper = new Wrapper<>(list);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(wrapper, outputStream);
    }
}
