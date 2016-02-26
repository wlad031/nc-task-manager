package ui;

import controllers.ControllerException;
import controllers.TaskController;
import dao.Dao;
import dao.DaoException;
import settings.SettingsException;
import views.JFrameTaskView;
import views.TaskView;
import views.TaskXmlDaoFactory;

public class Notifier extends Thread {

    private TaskController controller;

    public Notifier() throws UiException {

        try {
            Dao dao = new TaskXmlDaoFactory().createDao();
            TaskView view = new JFrameTaskView();
            controller = new TaskController(view, dao);

        } catch (DaoException e) {
            throw new UiException("Error in DAO", e);
        } catch (SettingsException e) {
            throw new UiException("Settings error", e);
        } catch (ControllerException e) {
            throw new UiException("Controller error", e);
        }
    }

    @Override
    public void run() {

        while (!this.isInterrupted()) {

            try {
                controller.request("now");
                Thread.sleep(60_000);
            } catch (InterruptedException e) {
                return;
            } catch (ControllerException e) {
                System.out.println("Error in the notifier");
                return;
            }
        }

        return;
    }
}
