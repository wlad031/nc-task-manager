import settings.SettingsException;
import ui.ConsoleUI;
import ui.Notifier;
import ui.UiException;

public class Main {

    public static void main(String[] args) throws SettingsException {

        try {
            Thread ui = new ConsoleUI();
            Thread notifier = new Notifier();

            ui.start();
            notifier.start();

            ui.join();

        } catch (UiException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
