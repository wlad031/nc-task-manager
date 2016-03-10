import dao.Dao;
import dao.XmlDao;
import dao.exceptions.DaoException;
import models.Model;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static org.junit.Assert.assertEquals;

public class XmlDaoTest {

    private static Dao xmlDao;
    private static String fileName;

    private static SimpleClass[] simpleObjects;

    @BeforeClass
    public static void init() throws DaoException {
        fileName = "test.xml";

        xmlDao = new XmlDao(fileName, SimpleClass.class);

        simpleObjects = new SimpleClass[]{
                new SimpleClass(0, "Gde nas net", 100500, true),
                new SimpleClass(1, "Hello", 32, true),
                new SimpleClass(2, "World1", 231, false)
        };
    }

    @Before
    public void prepareFile() throws DaoException {

        // Clean the file
        xmlDao.clear();

        // And write objects again
        for (SimpleClass so : simpleObjects) {
            xmlDao.add(so);
        }
    }

    @Test
    public void testGet() throws DaoException {

        for (int i = 0; i < simpleObjects.length; i++) {
            assertEquals(simpleObjects[i], xmlDao.get(i));
        }
    }

    @Test
    public void testUpdate() throws DaoException {
        int id = 1;

        SimpleClass temp = new SimpleClass(10, "blah", 666, false);
        xmlDao.update(id, temp);
        assertEquals(temp, xmlDao.get(temp.getId()));
    }

    @Test
    public void testRemove() throws DaoException {
        int id = 1;
        int oldCount = xmlDao.size();

        xmlDao.remove(id);

        assertEquals(oldCount - 1, xmlDao.size());

        try {
            xmlDao.get(id);
        } catch (DaoException e) {
            assert true;
        }
    }

    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class SimpleClass extends Model {

        @XmlElement
        private String myString;

        @XmlElement
        private Integer myInt;

        @XmlElement
        private Boolean myBoolean;

        public SimpleClass() {
            this(0, "a", 10, false);
        }

        public SimpleClass(int id, String s, int a, boolean b) {
            this.id = id;
            this.myString = s;
            this.myInt = a;
            this.myBoolean = b;
        }

        public String getMyString() {
            return myString;
        }

        public Integer getMyInt() {
            return myInt;
        }

        public Boolean getMyBoolean() {
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
