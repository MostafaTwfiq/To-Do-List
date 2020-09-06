package Tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import java.util.regex.Pattern;

public class Task {

    private final String dateTimeRegex = "(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01]) ([01][0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9])";

    private final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    private String title; // the title of the task
    private Vector<String> notes; // notes associated to the task
    private LocalDateTime dateTime; // the start date of the task
    private TaskStatus taskStatus; // the task status [done, not done, and in progress]

    public Task(String title, Vector<String> notes, LocalDateTime dateTime, TaskStatus taskStatus) {

        if (title == null || title.equals("") || notes == null || dateTime == null || taskStatus == null)
            throw new IllegalArgumentException();

        this.title = title;
        this.notes = notes;
        this.dateTime = dateTime;
        this.taskStatus = taskStatus;

    }

    public Task(String title, Vector<String> notes, String dateTime, TaskStatus taskStatus) {

        if (title == null || title.equals("") || notes == null || dateTime == null || taskStatus == null)
            throw new IllegalArgumentException();
        else if (!Pattern.matches(dateTimeRegex, dateTime))
            throw new IllegalArgumentException("The passed date in Task class in invalid");

        this.title = title;
        this.notes = notes;
        this.dateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(dateTimeFormat));
        this.taskStatus = taskStatus;

    }


    public void addNote(String note) {

        if (note == null || note.equals(""))
            throw new IllegalArgumentException();

        notes.add(note);
    }

    public void deleteNote(String note) {

        if (note == null)
            throw new IllegalArgumentException();

        notes.remove(note);
    }

    public void setDate(String dateTime) {
        if (!Pattern.matches(dateTimeRegex, dateTime))
            throw new IllegalArgumentException("The passed date in Task class in invalid");

        this.dateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyy-dd-MM HH:mm:ss tt"));

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.equals(""))
            throw new IllegalArgumentException();

        this.title = title;
    }

    public Vector<String> getNotes() {
        return notes;
    }

    public void setNotes(Vector<String> notes) {
        if (notes == null)
            throw new IllegalArgumentException();

        this.notes = notes;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        if (dateTime == null)
            throw new IllegalArgumentException();

        this.dateTime = dateTime;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {

        if (taskStatus == null)
            throw new IllegalArgumentException();

        this.taskStatus = taskStatus;
    }

}
