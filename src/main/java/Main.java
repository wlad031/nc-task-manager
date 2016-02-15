import java.io.File;

public class Main {


    private static File file;

    private static SimpleClass testObject;
    private static Dao dao;

    private static final String tString = "Hello, world!";
    private static final int tInt = 10;
    private static final boolean tBoolean = true;
    private static final Object tObject = new Object();

    public static void main(String[] args) {

        file = new File(Main.class.getClassLoader().getResource("xml_db.xml").getFile());

        testObject = new SimpleClass(1, tString, tInt, tBoolean);
        dao = new XmlFileDao(testObject.getClass(), file);

        dao.addModel(testObject);
    }
}
