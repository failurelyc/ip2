/** Represents a task occurring between a start and an end date or time. */
public class Event extends Task {
    private final String from;
    private final String to;

    /** Creates an incomplete event with its original date/time text. */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }

    /** Returns the original start date/time text. */
    public String getFrom() {
        return from;
    }

    /** Returns the original end date/time text. */
    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
