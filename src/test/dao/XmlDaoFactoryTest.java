package dao;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;


public class XmlDaoFactoryTest {

    private static XmlDaoFactory xmlDaoFactory;
    private static File file;
    private static Class objectClass;

    @BeforeClass
    public static void init() {
        xmlDaoFactory = new XmlDaoFactory();
        file = new File(XmlDaoFactory.class.getClassLoader().getResource("xml_db.xml").getFile());
        objectClass = XmlDaoFactoryTest.class;
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