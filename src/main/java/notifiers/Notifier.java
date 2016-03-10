package notifiers;

import controllers.TaskController;
import controllers.exceptions.ControllerException;
import dao.exceptions.DaoException;
import notifiers.exceptions.NotifierException;
import settings.exceptions.SettingsException;
import views.JFrameTaskView;
import dao.factories.TaskXmlDaoFactory;

public class Notifier extends Thread {

    private TaskController controller;
    private long checkDelay = 60_000;

    public Notifier() throws NotifierException {

        try {
            controller = new TaskController(new JFrameTaskView(), new TaskXmlDaoFactory().createDao());
        } catch (DaoException e) {
            throw new NotifierException("Error in DAO", e);
        } catch (SettingsException e) {
            throw new NotifierException("Settings error", e);
        } catch (ControllerException e) {
            throw new NotifierException("Controller error", e);
        }
    }

    @Override
    public void run() {

        while (!this.isInterrupted()) {

            try {
                controller.request("now");
                Thread.sleep(checkDelay);
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
