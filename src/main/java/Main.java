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

            while (!ui.isInterrupted()) {
            }

            notifier.interrupt();

            notifier.join();
            ui.join();

        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ControllerException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Goodbye!");
        }
    }
}
