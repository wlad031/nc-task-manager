import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class XmlDaoFactoryTest {

    private static XmlDaoFactory xmlDaoFactory;
    private static String file;
    private static Class objectClass;

    @BeforeClass
    public static void init() {
        xmlDaoFactory = new XmlDaoFactory();
        file = "xml_db.xml";
        objectClass = SimpleClass.class;
    }

    @Test
    public void testCreateDao() throws Exception {
        Dao xmlDao = xmlDaoFactory.createDao(file, objectClass);

        assertNotNull(xmlDao);

        if (xmlDao != null) {
            assertEquals(xmlDao.getClass(), XmlDao.class);
        }
    }

}