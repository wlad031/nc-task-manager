package views;

import models.TaskModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JFrameTaskView extends TaskView {

    private JFrame frame;
    private JLabel label;

    public JFrameTaskView() {

    }

    @Override
    public void show(List<TaskModel> list) {
        frame = new JFrame();
        label = new JLabel();

        createForm();

        TaskModel model = list.get(0);

        StringBuffer sb = new StringBuffer();
        sb.append("<html>");
        sb.append(model.getText() + "<br>");
        sb.append(TaskModel.getDateFormat().format(model.getDate()));
        sb.append("</html>");

        label.setText(sb.toString());

        frame.setVisible(true);
    }

    @Override
    public String read(TaskModel.Field field) {
        return null;
    }

    public void createForm() {

        Point frameSize = new Point(315, 250);
        int margin = 5;
        Point labelPos = new Point(margin, margin);

        label.setLocation(labelPos);

        frame.setSize((int) frameSize.getX(), (int) frameSize.getY());
        frame.setTitle("Notifier");
        frame.setResizable(false);

        frame.add(label);
    }
}
