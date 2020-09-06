package Tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import java.util.regex.Pattern;

public class Task {

    private final String dateRegex = "(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01]) ([01][0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9])";

    private final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    private String title; // the title of the task
    private Vector<String> notes; // notes
    private LocalDateTime dateTime; // the start date of the task

    public Task(String title, Vector<String> notes, LocalDateTime dateTime) {

        this.title = title;
        this.notes = notes;
        this.dateTime = dateTime;

    }

    public Task(String title, Vector<String> notes, String dateTime) {

        if (notes == null)
            throw new IllegalArgumentException("The passed notes vector is null in Task class.");
        else if (!Pattern.matches(dateRegex, dateTime))
            throw new IllegalArgumentException("The passed date in Task class in invalid");

        this.title = title;
        this.notes = notes;
        this.dateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(dateFormat));

    }


    public void addNote(String note) {
        notes.add(note);
    }

    public void deleteNote(String note) {
        notes.remove(note);
    }

    public void setDate(String dateTime) {
        if (!Pattern.matches(dateRegex, dateTime))
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


}
