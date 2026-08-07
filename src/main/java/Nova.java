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

            Command parsedCommand;
            try {
                parsedCommand = parser.parseCommand(command);
            } catch (IllegalArgumentException e) {
                System.out.println(" OOPS!!! " + e.getMessage());
                ui.showSeparator();
                continue;
            }
            if (parsedCommand != null) {
                parsedCommand.execute(tasks, ui, storage);
                ui.showSeparator();
                if (parsedCommand.isExit()) {
                    break;
                }
                continue;
            }

            if (command.startsWith("mark ")) {
                if (markTask(command.substring(5), tasks)) {
                    storage.save(tasks.asList());
                }
            } else if (command.startsWith("unmark ")) {
                if (unmarkTask(command.substring(7), tasks)) {
                    storage.save(tasks.asList());
                }
            } else {
                System.out.println(" OOPS!!! I don't recognise that command. Try todo, deadline, event, list, mark, unmark, or bye.");
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

}
