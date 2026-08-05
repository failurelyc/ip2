import java.util.Scanner;

/**
 * Runs Nova's command-line conversation.
 */
public class Nova {
    private static final int MAX_TASKS = 100;
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
        String[] tasks = new String[MAX_TASKS];
        int taskCount = 0;

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(SEPARATOR);

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(SEPARATOR);
                break;
            }

            if (command.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
            } else if (taskCount < MAX_TASKS) {
                tasks[taskCount] = command;
                taskCount++;
                System.out.println(" added: " + command);
            }

            System.out.println(SEPARATOR);
        }
    }
}
