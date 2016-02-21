import mvc.ControllerException;
import settings.SettingsException;

public class Main {

    public static void main(String[] args) throws ControllerException, SettingsException {
        new ConsoleUI().start();
    }
}
