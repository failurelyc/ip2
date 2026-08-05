/**
 * Represents a task in Nova's task list.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /** Creates an incomplete task with the given description. */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks this task as done. */
    public void markAsDone() {
        isDone = true;
    }

    /** Marks this task as not done. */
    public void markAsNotDone() {
        isDone = false;
    }

    /** Returns the status icon used when displaying this task. */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /** Returns this task's description. */
    public String getDescription() {
        return description;
    }
}
