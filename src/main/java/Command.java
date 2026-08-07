/** Represents one executable command in Nova's command loop. */
public abstract class Command {
    /** Executes this command using the application's collaborators. */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    /** Returns whether this command should terminate the application. */
    public boolean isExit() {
        return false;
    }
}
