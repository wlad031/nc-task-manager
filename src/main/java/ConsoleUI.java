import dao.Dao;
import dao.DaoException;
import mvc.Controller;
import mvc.ControllerException;
import settings.Settings;
import settings.SettingsException;
import task.TaskController;
import task.TaskView;
import task.TaskXmlDaoFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI extends Thread {

    TaskController controller;

    private final String welcome = "> ";

    public ConsoleUI() throws ControllerException {

        try {
            Dao dao = new TaskXmlDaoFactory().createDao();
            TaskView view = (TaskView) Class.forName((String) Settings.getInstance()
                    .getSettingValue(Settings.Setting.TASK_VIEW)).getConstructor().newInstance();

            controller = new TaskController(view, dao);

        } catch (DaoException e) {
            throw new ControllerException("DAO error", e);
        } catch (SettingsException e) {
            throw new ControllerException("Settings error", e);
        } catch (ClassNotFoundException e) {
            throw new ControllerException("Settings error", e);
        } catch (NoSuchMethodException e) {
            throw new ControllerException("Settings error", e);
        } catch (InstantiationException e) {
            throw new ControllerException("Settings error", e);
        } catch (IllegalAccessException e) {
            throw new ControllerException("Settings error", e);
        } catch (InvocationTargetException e) {
            throw new ControllerException("Settings error", e);
        }
    }

    @Override
    public void run() {

        Scanner scanner = new Scanner(System.in);

        while (!this.isInterrupted()) {
            System.out.print(welcome);

            try {
                String input = scanner.nextLine().toUpperCase();
                List<Object> commands = StringSeparator.separate(input);
                Command currentCommand = Command.valueOf((String) commands.get(0));

                switch (currentCommand) {
                    case HELP:
                        printHelp();
                        break;
                    case EXIT:
                        exit();
                        break;
                    case SHOW_ALL:
                        showAll();
                        break;
                    case ADD:
                        add();
                        break;
                    case SHOW:
                        show(Integer.parseInt((String) commands.get(1)));
                        break;
                    case UPDATE:
                        update(Integer.parseInt((String) commands.get(1)));
                        break;
                    case REMOVE:
                        remove(Integer.parseInt((String) commands.get(1)));
                        break;
                    default:
                        break;
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Unknown command: " + e.getMessage());
            } catch (ClassCastException e) {
                System.out.println("Illegal arguments: " + e.getMessage());
            } catch (ControllerException e) {
                System.out.println("Controller error: " + e.getMessage());
            }
        }
    }

    private void showAll() throws ControllerException {
        controller.action(Controller.Action.SHOW_ALL);
    }

    public void show(Integer i) throws ControllerException {
        controller.action(Controller.Action.SHOW, i);
    }

    public void add() throws ControllerException {
        controller.action(Controller.Action.ADD);
    }

    public void update(Integer i) throws ControllerException {
        controller.action(Controller.Action.UPDATE, i);
    }

    public void remove(Integer i) throws ControllerException {
        controller.action(Controller.Action.REMOVE, i);
    }

    private void printHelp() {
        System.out.println("ETO HELPA");
    }

    private void exit() {
        this.interrupt();
    }

    public enum Command {
        HELP,
        EXIT,
        SHOW_ALL,
        SHOW,
        ADD,
        UPDATE,
        REMOVE
    }
}
