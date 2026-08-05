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
        Task[] tasks = new Task[MAX_TASKS];
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
                    System.out.println(" " + (i + 1) + "." + tasks[i]);
                }
            } else if (command.startsWith("mark ")) {
                markTask(command.substring(5), tasks, taskCount);
            } else if (command.startsWith("unmark ")) {
                unmarkTask(command.substring(7), tasks, taskCount);
            } else if (taskCount < MAX_TASKS) {
                tasks[taskCount] = parseTask(command);
                taskCount++;
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount - 1]);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
            }

            System.out.println(SEPARATOR);
        }
    }

    /** Converts a user command into the appropriate task subtype. */
    private static Task parseTask(String command) {
        if (command.startsWith("deadline ")) {
            String body = command.substring(9);
            int marker = body.indexOf(" /by ");
            if (marker >= 0) {
                return new Deadline(body.substring(0, marker), body.substring(marker + 5));
            }
        } else if (command.startsWith("event ")) {
            String body = command.substring(6);
            int fromMarker = body.indexOf(" /from ");
            int toMarker = body.indexOf(" /to ");
            if (fromMarker >= 0 && toMarker > fromMarker) {
                return new Event(body.substring(0, fromMarker),
                        body.substring(fromMarker + 7, toMarker), body.substring(toMarker + 5));
            }
        } else if (command.startsWith("todo ")) {
            return new Todo(command.substring(5));
        }
        return new Todo(command);
    }

    /** Marks the task at the one-based index in a {@code mark} command as done. */
    private static void markTask(String taskNumber, Task[] tasks, int taskCount) {
        try {
            int index = Integer.parseInt(taskNumber) - 1;
            if (index < 0 || index >= taskCount) {
                System.out.println(" Task number is out of range.");
                return;
            }

            tasks[index].markAsDone();
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   [X] " + tasks[index].getDescription());
        } catch (NumberFormatException e) {
            System.out.println(" Please provide a valid task number.");
        }
    }

    /** Marks the task at the one-based index in an {@code unmark} command as not done. */
    private static void unmarkTask(String taskNumber, Task[] tasks, int taskCount) {
        try {
            int index = Integer.parseInt(taskNumber) - 1;
            if (index < 0 || index >= taskCount) {
                System.out.println(" Task number is out of range.");
                return;
            }

            tasks[index].markAsNotDone();
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   [ ] " + tasks[index].getDescription());
        } catch (NumberFormatException e) {
            System.out.println(" Please provide a valid task number.");
        }
    }
}
