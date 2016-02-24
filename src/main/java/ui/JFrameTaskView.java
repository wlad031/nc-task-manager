package ui;

import task.TaskModel;
import task.TaskView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JFrameTaskView extends TaskView {

    private JFrame frame;
    private JLabel label;
    private JButton finishButton;
    private JButton delayButton;

    public JFrameTaskView() {

    }

    @Override
    public void show(List<TaskModel> list) {
        frame = new JFrame();
        label = new JLabel();
        finishButton = new JButton();
        delayButton = new JButton();

        createForm();

        TaskModel model = list.get(0);

        StringBuffer sb = new StringBuffer();
        sb.append("<html>");
        sb.append(model.getTitle() + "<br>");
        sb.append(model.getText() + "<br>");
        sb.append(TaskModel.dateFormat.format(model.getDate()));
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
        Point buttonSize = new Point(150, 40);

        int margin = 5;

        Point labelPos = new Point(margin, margin);
        Point doneButtonPos = new Point(margin,
                (int) (frameSize.getY() - margin - 2 * buttonSize.getY()));
        Point delayButtonPos = new Point((int) (2 * margin + buttonSize.getX()),
                (int) (frameSize.getY() - margin - 2 * buttonSize.getY()));

        finishButton.setSize((int) buttonSize.getX(), (int) buttonSize.getY());
        finishButton.setText("Done");
        finishButton.setLocation(doneButtonPos);

        delayButton.setSize((int) buttonSize.getX(), (int) buttonSize.getY());
        delayButton.setText("Delay of 1 day");
        delayButton.setLocation(delayButtonPos);

        label.setLocation(labelPos);

        frame.setSize((int) frameSize.getX(), (int) frameSize.getY());
        frame.setTitle("Notifier");
        frame.setResizable(false);

//        frame.add(finishButton);
//        frame.add(delayButton);
        frame.add(label);
    }
}
