package ui;

import java.io.*;

public class Help {

    public static final String welcomeMessage =
            "Task Manager\ntype 'help' to get help, 'exit' to exit the program";

    public static String getText() {
        File helpFile = new File(Help.class.getClassLoader().getResource("help.dat").getFile());

        StringBuffer sb = new StringBuffer();

        try (BufferedReader reader = new BufferedReader(new FileReader(helpFile))) {

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                sb.append(currentLine + "\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
