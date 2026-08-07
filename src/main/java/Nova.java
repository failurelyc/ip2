import java.util.List;
import java.util.Scanner;

/**
 * Runs Nova's command-line conversation.
 */
public class Nova {
    private static final int MAX_TASKS = 100;

    /**
     * Starts Nova, echoes each command, and exits when {@code bye} is entered.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage("data/duke.txt");
        Parser parser = new Parser();
        TaskList tasks = new TaskList(storage.load());

        while (scanner.hasNextLine()) {
            String command = ui.readCommand(scanner);
            ui.showSeparator();

            Command parsedCommand = parser.parseCommand(command);
            if (parsedCommand != null) {
                parsedCommand.execute(tasks, ui, storage);
                ui.showSeparator();
                if (parsedCommand.isExit()) {
                    break;
                }
            }

            if (command.equals("list")) {
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + "." + tasks.get(i));
                }
            } else if (command.startsWith("mark ")) {
                if (markTask(command.substring(5), tasks)) {
                    storage.save(tasks.asList());
                }
            } else if (command.startsWith("unmark ")) {
                if (unmarkTask(command.substring(7), tasks)) {
                    storage.save(tasks.asList());
                }
            } else if (command.startsWith("delete ")) {
                if (deleteTask(command.substring(7), tasks)) {
                    storage.save(tasks.asList());
                }
            } else if (tasks.size() < MAX_TASKS) {
                try {
                    Task task = parser.parseTask(command);
                    tasks.add(task);
                    storage.save(tasks.asList());
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + task);
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                } catch (IllegalArgumentException e) {
                    System.out.println(" OOPS!!! " + e.getMessage());
                }
            } else {
                System.out.println(" OOPS!!! Your task list is full.");
            }

            ui.showSeparator();
        }
    }

    /** Marks the task at the one-based index in a {@code mark} command as done. */
    private static boolean markTask(String taskNumber, TaskList tasks) {
        try {
            int index = Integer.parseInt(taskNumber) - 1;
            if (index < 0 || index >= tasks.size()) {
                System.out.println(" Task number is out of range.");
                return false;
            }

            tasks.markAsDone(index);
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   [X] " + tasks.get(index).getDescription());
            return true;
        } catch (NumberFormatException e) {
            System.out.println(" Please provide a valid task number.");
            return false;
        }
    }

    /** Marks the task at the one-based index in an {@code unmark} command as not done. */
    private static boolean unmarkTask(String taskNumber, TaskList tasks) {
        try {
            int index = Integer.parseInt(taskNumber) - 1;
            if (index < 0 || index >= tasks.size()) {
                System.out.println(" Task number is out of range.");
                return false;
            }

            tasks.markAsNotDone(index);
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   [ ] " + tasks.get(index).getDescription());
            return true;
        } catch (NumberFormatException e) {
            System.out.println(" Please provide a valid task number.");
            return false;
        }
    }

    /** Deletes the task at the one-based index in a {@code delete} command. */
    private static boolean deleteTask(String taskNumber, TaskList tasks) {
        try {
            int index = Integer.parseInt(taskNumber) - 1;
            if (index < 0 || index >= tasks.size()) {
                System.out.println(" Task number is out of range.");
                return false;
            }

            Task deletedTask = tasks.remove(index);

            System.out.println(" Noted. I've removed this task:");
            System.out.println("   " + deletedTask);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            return true;
        } catch (NumberFormatException e) {
            System.out.println(" Please provide a valid task number.");
            return false;
        }
    }

}
