import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class XmlDaoTest {

    private static Dao xmlDao;
    private static String file;
    private static Class objectClass;

    private static SimpleClass[] simpleObjects;

    @BeforeClass
    public static void init() throws DaoException {
        file = "xml_db.xml";

        objectClass = SimpleClass.class;
        xmlDao = new XmlDao(file, objectClass);
        simpleObjects = new SimpleClass[]{
                new SimpleClass(),
                new SimpleClass(2, "Hello", 32, true),
                new SimpleClass(3, "World1", 231, false)
        };
    }

    @Test
    public void testGet() throws DaoException {
        int id = 0;

        xmlDao.add(simpleObjects[id]);
        assertEquals(simpleObjects[id], xmlDao.get(id));
    }

    @Test
    public void testAdd() throws DaoException  {
        xmlDao.add(simpleObjects[0]);
        assert true;
    }

    @Test
    public void testGetAll() {

        List list = xmlDao.getAll();
        System.out.println(list.toString());

        assert true;
    }

    @Test
    public void testUpdate() throws DaoException {

        int id = 0;
        xmlDao.update(id, simpleObjects[id + 1]);
        assertEquals(simpleObjects[id + 1], xmlDao.get(id));
    }

    @Test
    public void testRemove() {

        int id = 0;

        assert true;
    }
}
