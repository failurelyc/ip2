package nova.command;

import nova.storage.Storage;
import nova.task.TaskList;
import nova.ui.Ui;

/** Marks a numbered task as done and persists the change. */
public class MarkCommand extends Command {
    private final String taskNumber;

    /** Creates a mark command for the supplied one-based task number. */
    public MarkCommand(String taskNumber) { this.taskNumber = taskNumber; }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            int index = Integer.parseInt(taskNumber) - 1;
            if (index < 0 || index >= tasks.size()) {
                System.out.println(" Task number is out of range.");
                return;
            }
            tasks.markAsDone(index);
            storage.save(tasks.asList());
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   [X] " + tasks.get(index).getDescription());
        } catch (NumberFormatException e) {
            System.out.println(" Please provide a valid task number.");
        }
    }
}
