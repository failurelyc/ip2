import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/** Converts raw user commands into typed tasks. */
public class Parser {
    /** Converts an exit command into its command object, or returns {@code null}. */
    public Command parseCommand(String command) {
        if (command.equals("bye")) {
            return new ExitCommand();
        }
        if (command.equals("list")) {
            return new ListCommand();
        }
        if (command.startsWith("todo") || command.startsWith("deadline ") || command.startsWith("event ")) {
            return new AddCommand(parseTask(command));
        }
        return null;
    }

    /** Parses a task-creation command. */
    public Task parseTask(String command) {
        if (command.startsWith("deadline ")) {
            String body = command.substring(9);
            int marker = body.indexOf(" /by ");
            if (marker >= 0) {
                return new Deadline(requireDescription(body.substring(0, marker), "deadline"),
                        parseDeadlineDate(requireValue(body.substring(marker + 5), "deadline")));
            }
            throw new IllegalArgumentException("A deadline needs a description and a /by date.");
        } else if (command.startsWith("event ")) {
            String body = command.substring(6);
            int fromMarker = body.indexOf(" /from ");
            int toMarker = body.indexOf(" /to ");
            if (fromMarker >= 0 && toMarker > fromMarker) {
                return new Event(requireDescription(body.substring(0, fromMarker), "event"),
                        requireValue(body.substring(fromMarker + 7, toMarker), "event start time"),
                        requireValue(body.substring(toMarker + 5), "event end time"));
            }
            throw new IllegalArgumentException("An event needs a description, /from time, and /to time.");
        } else if (command.equals("todo") || command.startsWith("todo ")) {
            String description = command.equals("todo") ? "" : command.substring(5);
            return new Todo(requireDescription(description, "todo"));
        }
        throw new IllegalArgumentException("I don't recognise that command. Try todo, deadline, event, list, mark, unmark, or bye.");
    }

    private static String requireDescription(String value, String taskType) {
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("The description of a " + taskType + " cannot be empty.");
        }
        return value;
    }

    private static String requireValue(String value, String fieldName) {
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("The " + fieldName + " cannot be empty.");
        }
        return value;
    }

    private static LocalDateTime parseDeadlineDate(String value) {
        try {
            if (value.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
            }
            if (value.matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{4}")) {
                return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
            }
        } catch (DateTimeParseException e) {
            // Fall through to the user-friendly validation message below.
        }
        throw new IllegalArgumentException("The deadline must use yyyy-MM-dd or d/M/yyyy HHmm format.");
    }
}
