import notifiers.Notifier;
import notifiers.exceptions.NotifierException;
import settings.exceptions.SettingsException;
import ui.ConsoleUi;
import ui.exceptions.UiException;

public class Main {

    public static void main(String[] args) throws SettingsException {

        try {
            Thread ui = new ConsoleUi();
            Thread notifier = new Notifier();

            ui.start();
            notifier.start();

            ui.join();

        } catch (UiException | NotifierException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
