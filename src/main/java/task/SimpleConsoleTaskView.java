package task;

import java.util.List;
import java.util.Scanner;

public class SimpleConsoleTaskView implements TaskView {

    @Override
    public void show(List<TaskModel> list) {
        for (TaskModel model : list) {
            System.out.println(model.toString());
        }
    }

    @Override
    public Object read(String output) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(output);

        return scanner.nextLine();
    }
}
