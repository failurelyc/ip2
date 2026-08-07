import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Represents a task that must be completed by a specified date or time. */
public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_DATE = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter DISPLAY_TIME = DateTimeFormatter.ofPattern("h:mm a");
    private final LocalDateTime by;

    /** Creates an incomplete deadline with a typed date/time value. */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    /** Returns the deadline as a typed date/time value. */
    public LocalDateTime getBy() {
        return by;
    }

    /** Returns the deadline in a readable date format, including its time when present. */
    public String getFormattedBy() {
        String date = by.format(DISPLAY_DATE);
        return by.toLocalTime().equals(java.time.LocalTime.MIDNIGHT)
                ? date : date + " " + by.format(DISPLAY_TIME);
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + getFormattedBy() + ")";
    }
}
