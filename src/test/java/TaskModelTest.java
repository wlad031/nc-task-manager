import dao.Dao;
import dao.DaoException;
import dao.XmlDaoFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TaskModelTest {

    private static Dao dao;

    @BeforeClass
    public static void init() throws DaoException {

        dao = new XmlDaoFactory().createDao("tasks_db1.xml", TaskModel.class);
    }

    @Test

    public void testGetModel() throws DaoException {

        TaskModel task = new TaskModel();
        dao.add(task);

        int id = 0;

        //assertEquals(dao.get(id), TaskModel.getModel(id));
        assert false;
    }
}
