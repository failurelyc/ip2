package nova.command;

import nova.storage.Storage;
import nova.task.Task;
import nova.task.TaskList;
import nova.ui.Ui;

/** Adds a parsed task to Nova's list and persists the change. */
public class AddCommand extends Command {
    private static final int MAX_TASKS = 100;
    private final Task task;

    /** Creates an add command for the supplied task. */
    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.size() >= MAX_TASKS) {
            System.out.println(" OOPS!!! Your task list is full.");
            return;
        }
        tasks.add(task);
        storage.save(tasks.asList());
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
    }
}
