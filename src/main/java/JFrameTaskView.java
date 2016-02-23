import task.TaskModel;
import task.TaskView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JFrameTaskView extends TaskView {

    private JFrame frame;
    private Label label;

    public JFrameTaskView() {

    }

    @Override
    public void show(List<TaskModel> list) {

        frame = new JFrame();
        label = new Label();

        frame.setSize(250, 250);
        frame.setTitle("Notifier");

        frame.add(label);

        frame.setVisible(true);

        label.setText(list.toString());
    }

    @Override
    public String read(TaskModel.Field field) {
        return null;
    }
}
