import mvc.ControllerException;
import ui.ConsoleUI;
import ui.Notifier;

public class Main {

    public static void main(String[] args) {

        try {
            Thread ui = new ConsoleUI();
            Thread notifier = new Notifier();

            ui.start();
            notifier.start();

            ui.join();

        } catch (ControllerException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
