package views;

import models.TaskModel;
import settings.Settings;
import settings.exceptions.SettingsException;
import utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleConsoleTaskView extends TaskView {

    private Scanner scanner;

    // Default value
    private int lineLength = 48;

    public SimpleConsoleTaskView() throws SettingsException {
        scanner = new Scanner(System.in);
        lineLength = (int) Settings.getInstance().getSettingValue(Settings.Setting.CONSOLE_VIEW_LENGTH);
    }

    @Override
    public void show(List<TaskModel> list) {

        if (list.size() == 0) {
            System.out.println("We have no tasks");
            return;
        }

        String c = "-";
        StringBuffer sep = new StringBuffer();
        for (int i = 0; i < lineLength; i++) {
            sep.append(c);
        }

        System.out.println(sep);

        for (TaskModel model : list) {
            List<String> text = StringUtils.separate(model.getText(), lineLength);

            String printedLine = String.format("ID: %d\n", model.getId());
            System.out.printf("%" + (lineLength + 1) + "s", printedLine);
            System.out.println();

            text.forEach(System.out::println);

            System.out.println();
            System.out.printf("%" + lineLength + "s\n", model.getDateFormat().format(model.getDate()));

            if (model.isComplete()) {
                System.out.printf("%" + lineLength + "s\n", "DONE");
            }

            System.out.println(sep);
        }
    }

    @Override
    public List<String> read() {
        List<String> res = new ArrayList<>();

        System.out.println("Enter your task: ");
        res.add(scanner.nextLine());

        System.out.println("Enter date and time of the notify (" + TaskModel.getStringDateFormat() + "): ");
        res.add(scanner.nextLine());

        return res;
    }
}