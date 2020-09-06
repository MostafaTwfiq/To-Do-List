package Tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import java.util.regex.Pattern;

public class DayTasks {

    private final String dateRegex = "(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])";
    private final String dateFormat = "yyyy-MM-dd";
    private Vector<Task> tasks;
    private LocalDate date;

    public DayTasks(LocalDate date) {

        if (date == null)
            throw new IllegalArgumentException();

        tasks = new Vector<>();
        this.date = date;
    }

    public DayTasks(Vector<Task> tasks, LocalDate date) {
        if (tasks == null || date == null)
            throw new IllegalArgumentException();

        this.tasks = tasks;
        this.date = date;
    }

    public DayTasks(String date) {

        if (date == null)
            throw new IllegalArgumentException();
        else if (!Pattern.matches(dateRegex, date))
            throw new IllegalArgumentException("The passed date in Task class in invalid");

        tasks = new Vector<>();
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern(dateFormat));

    }

    public DayTasks(Vector<Task> tasks, String date) {

        if (tasks == null || date == null)
            throw new IllegalArgumentException();
        else if (!Pattern.matches(dateRegex, date))
            throw new IllegalArgumentException("The passed date in Task class in invalid");

        this.tasks = tasks;
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern(dateFormat));
    }

    public void addTask(Task task) {

        if (task == null)
            throw new IllegalArgumentException();

        tasks.add(task);

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {

        if (date == null)
            throw new IllegalArgumentException();

        this.date = date;
    }

    public void setDate(String date) {

        if (date == null)
            throw new IllegalArgumentException();
        else if (!Pattern.matches(dateRegex, date))
            throw new IllegalArgumentException("The passed date in Task class in invalid");

        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern(dateFormat));

    }

    public Vector<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Vector<Task> tasks) {

        if (tasks == null)
            throw new IllegalArgumentException();

        this.tasks = tasks;

    }

}
