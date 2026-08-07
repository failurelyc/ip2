import java.util.Scanner;

/** Handles Nova's direct interactions with the command-line user. */
public class Ui {
    private static final String SEPARATOR = "____________________________________________________________";

    /** Displays Nova's welcome message. */
    public void showWelcome() {
        showSeparator();
        String banner = " _   _  _____   ____   \n"
                + "| \\ | || ____| / ___|  \n"
                + "|  \\| ||  _|   \\___ \\  \n"
                + "| |\\  || |___   ___) | \n"
                + "|_| \\_||_____| |____/  \n";
        System.out.print(banner);
        System.out.println("Hello! I'm Nova.\nWhat can I do for you?");
        showSeparator();
    }

    /** Reads the next command from the user, or returns {@code null} at end of input. */
    public String readCommand(Scanner scanner) {
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }

    /** Displays the standard message separator. */
    public void showSeparator() {
        System.out.println(SEPARATOR);
    }

    /** Displays Nova's exit message. */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }
}
