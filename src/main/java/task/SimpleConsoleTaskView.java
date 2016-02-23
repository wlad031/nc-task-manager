package task;

import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class SimpleConsoleTaskView extends TaskView {

    private Scanner scanner;

    public SimpleConsoleTaskView() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void show(List<TaskModel> list) {
        for (TaskModel model : list) {
            System.out.println(model.toString());
        }
    }

    @Override
    public String read(TaskModel.Field field) {
        switch (field) {
            case TITLE:
                return readTitle();
            case TEXT:
                return readText();
            case DATE:
                return readDate();
            default:
                return null;
        }
    }

    protected String readTitle() {
        System.out.println("Enter the title: ");
        return scanner.nextLine();
    }

    protected String readText() {
        System.out.println("Enter the text: ");
        return scanner.nextLine();
    }

    protected String readDate() {
        System.out.println("Enter the date (dd.mm.yyyy hh:mm): ");
        return scanner.nextLine();
    }
}
