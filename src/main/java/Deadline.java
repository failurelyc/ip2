/** Represents a task that must be completed by a specified date or time. */
public class Deadline extends Task {
    private final String by;

    /** Creates an incomplete deadline with its original date/time text. */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    /** Returns the original date/time text after {@code /by}. */
    public String getBy() {
        return by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}
