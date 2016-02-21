import dao.Dao;
import dao.DaoException;
import mvc.Controller;
import mvc.ControllerException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import task.SimpleConsoleTaskView;
import task.TaskController;
import task.TaskModel;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TaskControllerTest {

    private static TaskController taskController;
    private static Dao dao;
    private static TaskModel testModel;

    private static String testTitle = "Test title";
    private static String testText = "Test text";
    private static String testDate = "January 2, 2010";

    @BeforeClass
    public static void init() throws ControllerException {
        taskController = new TaskController(new SimpleConsoleTaskView(), dao);
        dao = taskController.getDao();
    }

    @Before
    public void initTestModel() throws ParseException {
        Date date1 = TaskController.getDateFormat().parse(testDate);
        testModel = new TaskModel(dao.size(), testTitle, testText, date1);

        dao = taskController.getDao();
    }

    @Test
    public void testAddAction() throws ControllerException, DaoException, ParseException {
        taskController.action(Controller.Action.ADD, testTitle, testText, testDate);
        assertEquals(testModel, dao.get("id", dao.size() - 1));
    }

    @Test
    public void testUpdateAction() throws ControllerException, DaoException {
        String newTitle = "NEW TITLE";

        taskController.action(Controller.Action.ADD, testTitle, testText, testDate);

        taskController.action(Controller.Action.UPDATE, dao.size() - 1, newTitle, null, null);
        testModel.setTitle(newTitle);

        assertEquals(testModel, dao.get("id", dao.size() - 1));
    }

    @Test
    public void testRemoveAction() throws ControllerException, DaoException {
        taskController.action(Controller.Action.ADD, testTitle, testText, testDate);
        taskController.action(Controller.Action.REMOVE, dao.size() - 1);

        assertNull(dao.get("id", dao.size()));
    }
}
