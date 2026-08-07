package nova.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import nova.task.Deadline;
import nova.task.Event;
import nova.task.Task;
import nova.task.Todo;

/** Loads Nova tasks from disk and saves them back in a simple text format. */
public class Storage {
    private static final int MAX_TASKS = 100;
    private final Path path;

    /** Creates storage backed by the given file path. */
    public Storage(String filePath) {
        this.path = Path.of(filePath);
    }

    /** Loads valid tasks from the file, ignoring malformed entries. */
    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(path)) {
            return tasks;
        }
        try {
            for (String line : Files.readAllLines(path)) {
                if (!line.isBlank() && tasks.size() < MAX_TASKS) {
                    try {
                        tasks.add(parseStoredTask(line));
                    } catch (IllegalArgumentException e) {
                        // Ignore malformed lines so one bad entry does not prevent startup.
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(" OOPS!!! I couldn't load your saved tasks.");
        }
        return tasks;
    }

    /** Saves all tasks using one serialized task per line. */
    public void save(List<Task> tasks) {
        List<String> lines = tasks.stream().map(Storage::formatForStorage).toList();
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println(" OOPS!!! I couldn't save your tasks to disk.");
        }
    }

    private static Task parseStoredTask(String line) {
        String[] fields = line.split("\\s*\\|\\s*", -1);
        if (fields.length < 3) {
            throw new IllegalArgumentException("Invalid task data.");
        }
        Task task;
        switch (fields[0]) {
        case "D":
            if (fields.length != 4) {
                throw new IllegalArgumentException("Invalid deadline data.");
            }
            task = new Deadline(requireValue(fields[2], "deadline description"), parseStoredDeadline(fields[3]));
            break;
        case "E":
            if (fields.length != 5) {
                throw new IllegalArgumentException("Invalid event data.");
            }
            task = new Event(requireValue(fields[2], "event description"),
                    requireValue(fields[3], "event start time"), requireValue(fields[4], "event end time"));
            break;
        case "T":
            if (fields.length != 3) {
                throw new IllegalArgumentException("Invalid todo data.");
            }
            task = new Todo(requireValue(fields[2], "todo description"));
            break;
        default:
            throw new IllegalArgumentException("Unknown task type.");
        }
        if ("1".equals(fields[1])) {
            task.markAsDone();
        } else if (!"0".equals(fields[1])) {
            throw new IllegalArgumentException("Invalid task status.");
        }
        return task;
    }

    private static LocalDateTime parseStoredDeadline(String value) {
        try {
            return LocalDateTime.parse(value);
        } catch (DateTimeParseException e) {
            return parseDeadlineDate(value);
        }
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

    private static String requireValue(String value, String fieldName) {
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("The " + fieldName + " cannot be empty.");
        }
        return value;
    }

    private static String formatForStorage(Task task) {
        String status = task.isDone() ? "1" : "0";
        if (task instanceof Deadline deadline) {
            return "D | " + status + " | " + task.getDescription() + " | " + deadline.getBy();
        }
        if (task instanceof Event event) {
            return "E | " + status + " | " + task.getDescription() + " | " + event.getFrom()
                    + " | " + event.getTo();
        }
        return "T | " + status + " | " + task.getDescription();
    }
}
