import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Runs Nova's command-line conversation.
 */
public class Nova {
    private static final int MAX_TASKS = 100;
    private static final Path STORAGE_PATH = Path.of("data", "duke.txt");

    /**
     * Starts Nova, echoes each command, and exits when {@code bye} is entered.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage("data/duke.txt");
        Parser parser = new Parser();
        List<Task> tasks = storage.load();

        while (scanner.hasNextLine()) {
            String command = ui.readCommand(scanner);
            ui.showSeparator();

            if (command.equals("bye")) {
                ui.showGoodbye();
                ui.showSeparator();
                break;
            }

            if (command.equals("list")) {
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + "." + tasks.get(i));
                }
            } else if (command.startsWith("mark ")) {
                if (markTask(command.substring(5), tasks)) {
                    storage.save(tasks);
                }
            } else if (command.startsWith("unmark ")) {
                if (unmarkTask(command.substring(7), tasks)) {
                    storage.save(tasks);
                }
            } else if (command.startsWith("delete ")) {
                if (deleteTask(command.substring(7), tasks)) {
                    storage.save(tasks);
                }
            } else if (tasks.size() < MAX_TASKS) {
                try {
                    Task task = parser.parseTask(command);
                    tasks.add(task);
                    storage.save(tasks);
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + task);
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                } catch (IllegalArgumentException e) {
                    System.out.println(" OOPS!!! " + e.getMessage());
                }
            } else {
                System.out.println(" OOPS!!! Your task list is full.");
            }

            ui.showSeparator();
        }
    }

    /** Converts a user command into the appropriate task subtype. */
    private static Task parseTask(String command) {
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

    /** Returns a non-blank task description or reports the invalid input. */
    private static String requireDescription(String value, String taskType) {
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("The description of a " + taskType + " cannot be empty.");
        }
        return value;
    }

    /** Returns a non-blank command field or reports the invalid input. */
    private static String requireValue(String value, String fieldName) {
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("The " + fieldName + " cannot be empty.");
        }
        return value;
    }

    /** Parses supported deadline formats into a typed date/time value. */
    private static LocalDateTime parseDeadlineDate(String value) {
        try {
            if (value.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
            }
            if (value.matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{4}")) {
                return LocalDateTime.parse(value,
                        DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
            }
        } catch (DateTimeParseException e) {
            // Fall through to the user-friendly validation message below.
        }
        throw new IllegalArgumentException("The deadline must use yyyy-MM-dd or d/M/yyyy HHmm format.");
    }

    /** Marks the task at the one-based index in a {@code mark} command as done. */
    private static boolean markTask(String taskNumber, List<Task> tasks) {
        try {
            int index = Integer.parseInt(taskNumber) - 1;
            if (index < 0 || index >= tasks.size()) {
                System.out.println(" Task number is out of range.");
                return false;
            }

            tasks.get(index).markAsDone();
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   [X] " + tasks.get(index).getDescription());
            return true;
        } catch (NumberFormatException e) {
            System.out.println(" Please provide a valid task number.");
            return false;
        }
    }

    /** Marks the task at the one-based index in an {@code unmark} command as not done. */
    private static boolean unmarkTask(String taskNumber, List<Task> tasks) {
        try {
            int index = Integer.parseInt(taskNumber) - 1;
            if (index < 0 || index >= tasks.size()) {
                System.out.println(" Task number is out of range.");
                return false;
            }

            tasks.get(index).markAsNotDone();
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   [ ] " + tasks.get(index).getDescription());
            return true;
        } catch (NumberFormatException e) {
            System.out.println(" Please provide a valid task number.");
            return false;
        }
    }

    /** Deletes the task at the one-based index in a {@code delete} command. */
    private static boolean deleteTask(String taskNumber, List<Task> tasks) {
        try {
            int index = Integer.parseInt(taskNumber) - 1;
            if (index < 0 || index >= tasks.size()) {
                System.out.println(" Task number is out of range.");
                return false;
            }

            Task deletedTask = tasks.remove(index);

            System.out.println(" Noted. I've removed this task:");
            System.out.println("   " + deletedTask);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            return true;
        } catch (NumberFormatException e) {
            System.out.println(" Please provide a valid task number.");
            return false;
        }
    }

    /** Saves the current task list in a simple, one-task-per-line format. */
    private static void saveTasks(List<Task> tasks) {
        List<String> lines = tasks.stream().map(Nova::formatForStorage).toList();
        try {
            Files.createDirectories(STORAGE_PATH.getParent());
            Files.write(STORAGE_PATH, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println(" OOPS!!! I couldn't save your tasks to disk.");
        }
    }

    /** Loads previously saved tasks, or starts with an empty list if no file exists. */
    private static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(STORAGE_PATH)) {
            return tasks;
        }

        try {
            for (String line : Files.readAllLines(STORAGE_PATH)) {
                if (line.isBlank() || tasks.size() >= MAX_TASKS) {
                    continue;
                }
                try {
                    tasks.add(parseStoredTask(line));
                } catch (IllegalArgumentException e) {
                    // Ignore malformed lines so one bad entry does not prevent startup.
                }
            }
        } catch (IOException e) {
            System.out.println(" OOPS!!! I couldn't load your saved tasks.");
        }
        return tasks;
    }

    /** Converts one stored line back into its task subtype and completion status. */
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
            task = new Deadline(requireValue(fields[2], "deadline description"),
                    parseStoredDeadline(fields[3]));
            break;
        case "E":
            if (fields.length != 5) {
                throw new IllegalArgumentException("Invalid event data.");
            }
            task = new Event(requireValue(fields[2], "event description"),
                    requireValue(fields[3], "event start time"),
                    requireValue(fields[4], "event end time"));
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

    /** Parses the ISO date/time representation used in the task data file. */
    private static LocalDateTime parseStoredDeadline(String value) {
        try {
            return LocalDateTime.parse(value);
        } catch (DateTimeParseException e) {
            return parseDeadlineDate(value);
        }
    }

    /** Converts a task to the format used by the task data file. */
    private static String formatForStorage(Task task) {
        String status = task.isDone ? "1" : "0";
        if (task instanceof Deadline deadline) {
            return "D | " + status + " | " + task.getDescription() + " | " + deadline.getBy();
        }
        if (task instanceof Event event) {
            return "E | " + status + " | " + task.getDescription() + " | "
                    + event.getFrom() + " | " + event.getTo();
        }
        return "T | " + status + " | " + task.getDescription();
    }
}
