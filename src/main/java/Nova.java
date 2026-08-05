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
                try {
                    Task task = parseTask(command);
                    tasks[taskCount] = task;
                    taskCount++;
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + task);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                } catch (IllegalArgumentException e) {
                    System.out.println(" OOPS!!! " + e.getMessage());
                }
            } else {
                System.out.println(" OOPS!!! Your task list is full.");
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
                return new Deadline(requireDescription(body.substring(0, marker), "deadline"),
                        requireValue(body.substring(marker + 5), "deadline"));
            }
            throw new IllegalArgumentException("A deadline needs a description and a /by date.");
        } else if (command.startsWith("event ")) {
            String body = command.substring(6);
            int fromMarker = body.indexOf(" /from ");
            int toMarker = body.indexOf(" /to ");
            if (fromMarker >= 0 && toMarker > fromMarker) {
                return new Event(requireDescription(body.substring(0, fromMarker), "event"),
                        requireValue(body.substring(fromMarker + 7, toMarker), "event start time"),
                        requireValue(body.substring(toMarker + 5), "event end time"));
            }
            throw new IllegalArgumentException("An event needs a description, /from time, and /to time.");
        } else if (command.equals("todo") || command.startsWith("todo ")) {
            String description = command.equals("todo") ? "" : command.substring(5);
            return new Todo(requireDescription(description, "todo"));
        }
        throw new IllegalArgumentException("I don't recognise that command. Try todo, deadline, event, list, mark, unmark, or bye.");
    }

    /** Returns a non-blank task description or reports the invalid input. */
    private static String requireDescription(String value, String taskType) {
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("The description of a " + taskType + " cannot be empty.");
        }
        return value;
    }

    /** Returns a non-blank command field or reports the invalid input. */
    private static String requireValue(String value, String fieldName) {
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("The " + fieldName + " cannot be empty.");
        }
        return value;
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
