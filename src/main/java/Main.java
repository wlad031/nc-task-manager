import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        final Class clazz = SimpleClass.class;

        InputStream is = XmlDaoFactory.class.getClassLoader().getResourceAsStream("xml_db.xml");
        OutputStream os = new FileOutputStream(new File(XmlDaoFactory.class.getClassLoader().getResource("xml_db.xml").getFile()));

        SimpleClass object = new SimpleClass();
        SimpleClass o2 = new SimpleClass(3, "zhopa", 32, true);

        List l = new ArrayList();
        l.add(object);
        l.add(o2);

        try {
            MarshallerAdapter marshallerAdapter = new MarshallerAdapter(clazz);
            marshallerAdapter.marshal(l, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        is.close();
    }

}
