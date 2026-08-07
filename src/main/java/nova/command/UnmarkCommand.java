package nova.command;

import nova.storage.Storage;
import nova.task.TaskList;
import nova.ui.Ui;

/** Marks a numbered task as not done and persists the change. */
public class UnmarkCommand extends Command {
    private final String taskNumber;

    /** Creates an unmark command for the supplied one-based task number. */
    public UnmarkCommand(String taskNumber) { this.taskNumber = taskNumber; }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            int index = Integer.parseInt(taskNumber) - 1;
            if (index < 0 || index >= tasks.size()) {
                System.out.println(" Task number is out of range.");
                return;
            }
            tasks.markAsNotDone(index);
            storage.save(tasks.asList());
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   [ ] " + tasks.get(index).getDescription());
        } catch (NumberFormatException e) {
            System.out.println(" Please provide a valid task number.");
        }
    }
}
