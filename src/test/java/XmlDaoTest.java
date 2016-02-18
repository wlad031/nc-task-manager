import dao.Dao;
import dao.DaoException;
import dao.XmlDao;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.bind.annotation.*;
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
        fileName = "test.xml";

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

    @XmlRootElement(name = "test")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class SimpleClass {

        @XmlAttribute
        public int id;

        @XmlElement
        private String myString;

        @XmlElement
        private int myInt;

        @XmlElement
        private boolean myBoolean;

        public SimpleClass() {
            this(0, "a", 10, false);
        }

        public SimpleClass(int id, String s, int a, boolean b) {
            this.id = id;
            this.myString = s;
            this.myInt = a;
            this.myBoolean = b;
        }

        public int getId() {
            return id;
        }

        public String getMyString() {
            return myString;
        }

        public int getMyInt() {
            return myInt;
        }

        public boolean isMyBoolean() {
            return myBoolean;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SimpleClass that = (SimpleClass) o;

            if (myInt != that.myInt) return false;
            if (myBoolean != that.myBoolean) return false;
            return !(myString != null ? !myString.equals(that.myString) : that.myString != null);

        }

        @Override
        public int hashCode() {
            int result = myString != null ? myString.hashCode() : 0;
            result = 31 * result + myInt;
            result = 31 * result + (myBoolean ? 1 : 0);
            return result;
        }
    }
}
