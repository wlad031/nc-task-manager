import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

public class XmlFileDao extends Dao {

    public XmlFileDao(Class modelClass, File file) {
        super(modelClass, file);
    }

    @Override
    public Object getModel(int id) {
        return null;
    }

    @Override
    public List<Object> getAllModels() {
        return null;
    }

    @Override
    public void updateModel(int id, Object model) {

    }

    @Override
    public void addModel(Object model) {

        try {
            JAXBContext context = JAXBContext.newInstance(modelClass);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(model, file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeModel(int id) {

    }
}
