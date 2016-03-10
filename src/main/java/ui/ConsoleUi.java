package ui;

import controllers.TaskController;
import controllers.exceptions.ControllerException;
import dao.exceptions.DaoException;
import dao.factories.TaskXmlDaoFactory;
import settings.Settings;
import settings.exceptions.SettingsException;
import ui.exceptions.UiException;
import utils.Parser;
import utils.StringUtils;
import views.TaskView;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleUi extends Thread {

    private TaskController controller;

    private String welcomeSymbol;

    public ConsoleUi() throws UiException {

        try {
            TaskView view = (TaskView) Class.forName((String) Settings.getInstance()
                    .getSettingValue(Settings.Setting.TASK_VIEW)).getConstructor().newInstance();

            controller = new TaskController(view, new TaskXmlDaoFactory().createDao());

            String welcomeMessage = (String) Settings.getInstance().getSettingValue(Settings.Setting.WELCOME_MESSAGE);
            System.out.println(welcomeMessage);

            welcomeSymbol = (String) Settings.getInstance().getSettingValue(Settings.Setting.WELCOME_SYMBOL);

        } catch (SettingsException | NoSuchMethodException |
                InstantiationException | ClassNotFoundException |
                IllegalAccessException | InvocationTargetException e) {

            throw new UiException("Settings error", e);

        } catch (DaoException e) {
            throw new UiException("DAO error", e);
        } catch (ControllerException e) {
            throw new UiException("Controller error", e);
        }
    }

    @Override
    public void run() {

        Scanner scanner = new Scanner(System.in);

        while (!this.isInterrupted()) {

            System.out.print(welcomeSymbol);

            try {
                String input = scanner.nextLine().toUpperCase();
                List<String> commands = StringUtils.separate(input);
                Command currentCommand = Command.valueOf(commands.get(0));
                List<String> params = new ArrayList<>(commands.subList(1, commands.size()));

                switch (currentCommand) {
                    case HELP:
                        System.out.println(Help.getText());
                        break;
                    case EXIT:
                        this.interrupt();
                        break;
                    case ADD:
                        controller.request("add");
                        break;
                    case SHOW:
                        controller.request("show", Parser.parseInt(params.toArray()));
                        break;
                    case REMOVE:
                        controller.request("remove", Parser.parseInt(params.toArray()));
                        break;
                    case UPDATE:
                        controller.request("update", Parser.parseInt(params.toArray()));
                        break;
                    case COMPLETE:
                        controller.request("complete", Parser.parseInt(Arrays.asList(params.get(0)).toArray()));
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

        return;
    }

    public enum Command {
        HELP,
        EXIT,
        SHOW,
        ADD,
        UPDATE,
        REMOVE,
        COMPLETE
    }
}
