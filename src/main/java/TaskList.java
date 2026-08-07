import java.util.ArrayList;
import java.util.List;

/** Owns Nova's tasks and provides operations for changing the collection. */
public class TaskList {
    private final List<Task> tasks;

    /** Creates a task list containing the supplied tasks. */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /** Returns the number of tasks currently stored. */
    public int size() { return tasks.size(); }

    /** Returns the task at the zero-based index. */
    public Task get(int index) { return tasks.get(index); }

    /** Adds a task to the end of the list. */
    public void add(Task task) { tasks.add(task); }

    /** Marks the task at the zero-based index as done. */
    public void markAsDone(int index) { tasks.get(index).markAsDone(); }

    /** Marks the task at the zero-based index as not done. */
    public void markAsNotDone(int index) { tasks.get(index).markAsNotDone(); }

    /** Removes and returns the task at the zero-based index. */
    public Task remove(int index) { return tasks.remove(index); }

    /** Returns a snapshot suitable for persistence. */
    public List<Task> asList() { return new ArrayList<>(tasks); }
}
