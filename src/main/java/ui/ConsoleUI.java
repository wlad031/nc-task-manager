package ui;

import dao.Dao;
import dao.DaoException;
import mvc.Controller;
import mvc.ControllerException;
import settings.Settings;
import settings.SettingsException;
import task.TaskController;
import task.TaskView;
import task.TaskXmlDaoFactory;
import utils.StringSeparator;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI extends Thread {

    private TaskController controller;

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
                List<String> commands = StringSeparator.separate(input);
                Command currentCommand = Command.valueOf(commands.get(0));
                List<String> params = new ArrayList<>(commands.subList(1, commands.size()));

                switch (currentCommand) {
                    case HELP:
                        printHelp();
                        break;
                    case EXIT:
                        exit();
                        break;
                    case ADD:
                        add();
                        break;
                    case SHOW:
                        show(params.toArray());
                        break;
                    case UPDATE:
                        update(params.toArray());
                        break;
                    case REMOVE:
                        remove(params.toArray());
                        break;
                    default:
                        System.out.println("Not available command");
                        break;
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Unknown command: " + e.getMessage());
            } catch (ClassCastException e) {
                e.printStackTrace();
                System.out.println("Illegal arguments: " + e.getMessage());
            } catch (ControllerException e) {
                System.out.println("Controller error: " + e.getMessage());
            }
        }
    }

    public <T> void show(T... ids) throws ControllerException {
        controller.action(Controller.Action.SHOW, ids);
    }

    public void add() throws ControllerException {
        controller.action(Controller.Action.ADD);
    }

    public <T> void update(T... ids) throws ControllerException {
        controller.action(Controller.Action.UPDATE, ids);
    }

    public <T> void remove(T... ids) throws ControllerException {
        controller.action(Controller.Action.REMOVE, ids);
    }

    private void printHelp() {
        System.out.println(Help.getText());
    }

    private void exit() {
        this.interrupt();
    }

    public enum Command {
        HELP,
        EXIT,
        SHOW,
        ADD,
        UPDATE,
        REMOVE
    }
}
