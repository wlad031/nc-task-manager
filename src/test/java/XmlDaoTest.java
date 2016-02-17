import dao.Dao;
import dao.DaoException;
import dao.XmlDao;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class XmlDaoTest {

    private static Dao xmlDao;
    private static String fileName;

    private static SimpleClass[] simpleObjects;

    @BeforeClass
    public static void init() throws DaoException {
        fileName = "xml_db.xml";

        xmlDao = new XmlDao(fileName, SimpleClass.class);

        simpleObjects = new SimpleClass[]{
                new SimpleClass(),
                new SimpleClass(2, "Hello", 32, true),
                new SimpleClass(3, "World1", 231, false)
        };
    }

    @Before
    public void prepareFile() throws DaoException {

        // Clean the file
        File file = new File(XmlDaoTest.class.getClassLoader().getResource(fileName).getFile());
        try (PrintWriter pw = new PrintWriter(new FileWriter(file, false), false)) {
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // And write objects again
        for (SimpleClass so : simpleObjects) {
            xmlDao.add(so);
        }
    }

    @Test
    public void testGetNAdd() throws DaoException {
        int id = 0;

        for (int i = 0; i < simpleObjects.length; i++) {
            assertEquals(simpleObjects[id], xmlDao.get(id));
        }
    }

    @Test
    public void testUpdate() throws DaoException {
        int id = 0;

        xmlDao.update(id, simpleObjects[id + 1]);
        assertEquals(simpleObjects[id + 1], xmlDao.get(id));
    }

    @Test
    public void testRemove() throws DaoException {
        int id = 0;

        int oldCount = xmlDao.getAll().size();

        xmlDao.remove(id);

        int newCount = xmlDao.getAll().size();

        assertEquals(oldCount - 1, newCount);

        for (int i = 0; i < simpleObjects.length; i++) {
            if (i < id) {
                assertEquals(simpleObjects[id], xmlDao.get(id));
            } else {
                assertEquals(simpleObjects[id + 1], xmlDao.get(id));
            }
        }
    }
}
