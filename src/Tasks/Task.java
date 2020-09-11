package Tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import java.util.regex.Pattern;

public class Task {

    private String title; // the title of the task
    private Vector<String> notes; // notes associated to the task
    private LocalDateTime dateTime; // the start date of the task
    private TaskStatus taskStatus; // the task status [done, not done, and in progress]
    private TaskPriority priority;

    public Task(String title, Vector<String> notes, LocalDateTime dateTime, TaskStatus taskStatus, TaskPriority priority) {

        if (title == null || title.equals("") || notes == null || dateTime == null || taskStatus == null)
            throw new IllegalArgumentException();

        this.title = title;
        this.notes = notes;
        this.dateTime = dateTime;
        this.taskStatus = taskStatus;
        this.priority = priority;

    }

    public Task(String title, Vector<String> notes, String dateTime, TaskStatus taskStatus, TaskPriority priority) {

        if (title == null || title.equals("") || notes == null || dateTime == null || taskStatus == null)
            throw new IllegalArgumentException();
        else if (!Pattern.matches(DateFormat.getDateTimeRegex(), dateTime))
            throw new IllegalArgumentException("The passed date in Task class in invalid");

        this.title = title;
        this.notes = notes;
        this.dateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DateFormat.getDateTimeFormat()));
        this.taskStatus = taskStatus;
        this.priority = priority;

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
        if (!Pattern.matches(DateFormat.getDateTimeRegex(), dateTime))
            throw new IllegalArgumentException("The passed date in Task class in invalid");

        this.dateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DateFormat.getDateTimeFormat()));

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

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {

        if (priority == null)
            throw new IllegalArgumentException();

        this.priority = priority;
    }
}
