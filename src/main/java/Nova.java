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
        boolean[] completed = new boolean[MAX_TASKS];
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
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    String status = completed[i] ? "X" : " ";
                    System.out.println(" " + (i + 1) + ".[" + status + "] " + tasks[i]);
                }
            } else if (command.startsWith("mark ")) {
                markTask(command.substring(5), tasks, completed, taskCount);
            } else if (command.startsWith("unmark ")) {
                unmarkTask(command.substring(7), tasks, completed, taskCount);
            } else if (taskCount < MAX_TASKS) {
                tasks[taskCount] = command;
                taskCount++;
                System.out.println(" added: " + command);
            }

            System.out.println(SEPARATOR);
        }
    }

    /** Marks the task at the one-based index in a {@code mark} command as done. */
    private static void markTask(String taskNumber, String[] tasks, boolean[] completed, int taskCount) {
        try {
            int index = Integer.parseInt(taskNumber) - 1;
            if (index < 0 || index >= taskCount) {
                System.out.println(" Task number is out of range.");
                return;
            }

            completed[index] = true;
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   [X] " + tasks[index]);
        } catch (NumberFormatException e) {
            System.out.println(" Please provide a valid task number.");
        }
    }

    /** Marks the task at the one-based index in an {@code unmark} command as not done. */
    private static void unmarkTask(String taskNumber, String[] tasks, boolean[] completed, int taskCount) {
        try {
            int index = Integer.parseInt(taskNumber) - 1;
            if (index < 0 || index >= taskCount) {
                System.out.println(" Task number is out of range.");
                return;
            }

            completed[index] = false;
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   [ ] " + tasks[index]);
        } catch (NumberFormatException e) {
            System.out.println(" Please provide a valid task number.");
        }
    }
}
