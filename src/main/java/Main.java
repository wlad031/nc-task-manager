import mvc.ControllerException;
import settings.SettingsException;
import ui.ConsoleUI;
import ui.Notifier;

public class Main {

    public static void main(String[] args) throws ControllerException, SettingsException {

        Thread ui = new ConsoleUI();
        ui.start();

        Thread notifier = new Notifier();
        notifier.setDaemon(true);
        notifier.start();
    }
}
