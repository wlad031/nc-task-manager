package ui;

import dao.Dao;
import dao.DaoException;
import mvc.ControllerException;
import settings.SettingsException;
import task.TaskController;
import task.TaskView;
import task.TaskXmlDaoFactory;

public class Notifier extends Thread {

    private TaskController controller;

    public Notifier() {

        try {
            Dao dao = new TaskXmlDaoFactory().createDao();
            TaskView view = new JFrameTaskView();
            controller = new TaskController(view, dao);

        } catch (DaoException e) {
            e.printStackTrace();
        } catch (SettingsException e) {
            e.printStackTrace();
        } catch (ControllerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true) {

            if (!this.isInterrupted()) {

                try {
                    controller.showNowTasks();
                    Thread.sleep(60_000);
                } catch (DaoException e) {
                    System.out.println("Object access error");
                } catch (InterruptedException e) {
                    return;
                }

            } else {
                return;
            }
        }
    }
}
