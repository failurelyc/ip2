package nova;

import java.util.Scanner;

import nova.command.Command;
import nova.parser.Parser;
import nova.storage.Storage;
import nova.task.TaskList;
import nova.ui.Ui;

/**
 * Runs Nova's command-line conversation.
 */
public class Nova {
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

            System.out.println(" OOPS!!! I don't recognise that command. Try todo, deadline, event, "
                    + "list, mark, unmark, or bye.");

            ui.showSeparator();
        }
    }

}
