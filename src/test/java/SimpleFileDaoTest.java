import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

public class SimpleFileDaoTest {

    private static File file;

    private static TestClass testObject;
    private static Dao dao;

    private static final String tString = "Hello, world!";
    private static final int tInt = 10;
    private static final boolean tBoolean = true;
    private static final Object tObject = new Object();

    @BeforeClass
    public static void init() {
        file = new File(SimpleFileDaoTest.class.getClassLoader().getResource("simple_file_db.dat").getFile());

        testObject = new TestClass(tString, tInt, tBoolean, tObject);
        dao = new SimpleFileDao(testObject.getClass(), file);
    }

    @Test
    public void modelAdding() {
        dao.addModel(testObject);
    }

    private static class TestClass {
        private String s;
        private int a;
        private boolean b;
        private Object o;

        public TestClass(String s, int a, boolean b, Object o) {
            this.s = s;
            this.a = a;
            this.b = b;
            this.o = o;
        }

        @Getter(fieldName = "s")
        public String getS() {
            return s;
        }

        @Getter(fieldName = "a")
        public int getA() {
            return a;
        }

        @Getter(fieldName = "b")
        public boolean isB() {
            return b;
        }

        @Getter(fieldName = "o")
        public Object getO() {
            return o;
        }
    }
}
