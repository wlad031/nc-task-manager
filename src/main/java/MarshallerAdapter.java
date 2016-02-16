import jdk.internal.org.xml.sax.SAXParseException;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MarshallerAdapter<T> {

    private JAXBContext context;

    public MarshallerAdapter(Class<T> clazz) throws JAXBException {
        context = JAXBContext.newInstance(Wrapper.class, clazz);
    }

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

    public void marshal(List<T> list, OutputStream outputStream) throws JAXBException {

        Wrapper<T> wrapper = new Wrapper<>(list);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(wrapper, outputStream);
    }
}
