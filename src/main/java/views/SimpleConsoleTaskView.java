package views;

import models.TaskModel;
import utils.StringUtils;

import java.util.List;
import java.util.Scanner;

public class SimpleConsoleTaskView extends TaskView {

    private Scanner scanner;
    private int lineLength = 48;

    public SimpleConsoleTaskView() {

        scanner = new Scanner(System.in);
    }

    @Override
    public void show(List<TaskModel> list) {

        String c = "-";
        StringBuffer sep = new StringBuffer();
        for (int i = 0; i < lineLength; i++) {
            sep.append(c);
        }

        System.out.println(sep);

        for (TaskModel model : list) {
            List<String> text = StringUtils.separate(model.getText(), lineLength);

            System.out.printf("%" + (lineLength - 4) + "s%4d\n", "ID: ", model.getId());
            System.out.println();

            for (String line : text) {
                System.out.println(line);
            }

            System.out.println();
            System.out.printf("%" + lineLength + "s\n", model.getDateFormat().format(model.getDate()));

            if (model.isComplete()) {
                System.out.printf("%" + lineLength + "s\n", "DONE");
            }

            System.out.println(sep);
        }
    }

    @Override
    public String read(TaskModel.Field field) {
        switch (field) {
            case TEXT:
                return readText();
            case DATE:
                return readDate();
            default:
                return null;
        }
    }


    protected String readText() {
        System.out.println("Enter your task: ");
        return scanner.nextLine();
    }

    protected String readDate() {
        System.out.println("Enter date and time of the notify (" + TaskModel.getStringDateFormat() + "): ");
        return scanner.nextLine();
    }
}
