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
import java.util.Scanner;

public class ConsoleUI extends Thread {

    TaskController controller;

    private final String welcome = "> ";

    public ConsoleUI() throws ControllerException {

        try {
            Dao dao = new TaskXmlDaoFactory().createDao();
            TaskView view = (TaskView) Class.forName((String) Settings.getInstance().
                    getSettingValue(Settings.Setting.TASK_VIEW)).getConstructor().newInstance();

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
                Command currentCommand = Command.valueOf(scanner.nextLine().toUpperCase());

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
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Unknown command");
            }
        }
    }

    private void showAll() {
        try {
            controller.action(Controller.Action.SHOW_ALL);
        } catch (ControllerException e) {
            System.out.println(e.getMessage());
        }
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
        SHOW_ALL
    }
}
