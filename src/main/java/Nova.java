import java.util.Scanner;

/**
 * Runs Nova's command-line conversation.
 */
public class Nova {
    private static final String SEPARATOR = "____________________________________________________________";

    /**
     * Starts Nova, echoes each command, and exits when {@code bye} is entered.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        System.out.println(SEPARATOR);
        String banner = " _   _  _____   ____   \n"
                + "| \\ | || ____| / ___|  \n"
                + "|  \\| ||  _|   \\___ \\  \n"
                + "| |\\  || |___   ___) | \n"
                + "|_| \\_||_____| |____/  \n";
        System.out.print(banner);
        System.out.println("Hello! I'm Nova.\nWhat can I do for you?");
        System.out.println(SEPARATOR);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(SEPARATOR);

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(SEPARATOR);
                break;
            }

            System.out.println(" " + command);
            System.out.println(SEPARATOR);
        }
    }
}
