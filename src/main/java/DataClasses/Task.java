package DataClasses;

import DataClasses.TaskStatus.TaskPriority;
import DataClasses.TaskStatus.TaskStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Task {

    private int taskID; // the unique id of the task
    private String title; // the title of the task
    private List<String> notes; // notes associated to the task
    private List<String> tags; // tags associated to the task
    private LocalDateTime dateTime; // the start date of the task
    private TaskStatus taskStatus; // the task status [done, not done, and in progress]
    private TaskPriority priority; // the priority of the task
    private boolean isStarred; // represents the status of the task where is it starred or not

    public Task(int taskID, String title,
                List<String> notes, List<String> tags,
                LocalDateTime dateTime,
                TaskStatus taskStatus, TaskPriority priority,
                boolean isStarred) {

        if (taskID < 1 || title == null || title.equals("") || notes == null || tags == null || dateTime == null || taskStatus == null)
            throw new IllegalArgumentException();

        this.taskID = taskID;
        this.title = title;
        this.notes = notes;
        this.tags = tags;
        this.dateTime = dateTime;
        this.taskStatus = taskStatus;
        this.priority = priority;
        this.isStarred = isStarred;

    }

    public Task(int taskID, String title,
                LocalDateTime dateTime,
                TaskStatus taskStatus, TaskPriority priority,
                boolean isStarred) {

        if (taskID < 1 || title == null || title.equals("") || dateTime == null || taskStatus == null)
            throw new IllegalArgumentException();

        this.taskID = taskID;
        this.title = title;
        this.notes = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.dateTime = dateTime;
        this.taskStatus = taskStatus;
        this.priority = priority;
        this.isStarred = isStarred;

    }

    public Task(int taskID, String title,
                List<String> notes, List<String> tags,
                String dateTime, TaskStatus taskStatus,
                TaskPriority priority,
                boolean isStarred) {

        if (taskID < 0 || title == null || title.equals("") || notes == null || tags == null || dateTime == null || taskStatus == null)
            throw new IllegalArgumentException();
        else if (!Pattern.matches(DateFormat.getDateTimeRegex(), dateTime))
            throw new IllegalArgumentException("The passed date in Task class in invalid");

        this.taskID = taskID;
        this.title = title;
        this.notes = notes;
        this.tags = tags;
        this.dateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DateFormat.getDateTimeFormat()));
        this.taskStatus = taskStatus;
        this.priority = priority;
        this.isStarred = isStarred;

    }

    public Task(int taskID, String title,
                String dateTime,
                TaskStatus taskStatus,
                TaskPriority priority,
                boolean isStarred) {

        if (taskID < 0 || title == null || title.equals("") || dateTime == null || taskStatus == null)
            throw new IllegalArgumentException();
        else if (!Pattern.matches(DateFormat.getDateTimeRegex(), dateTime))
            throw new IllegalArgumentException("The passed date in Task class in invalid");

        this.taskID = taskID;
        this.title = title;
        this.notes = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.dateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DateFormat.getDateTimeFormat()));
        this.taskStatus = taskStatus;
        this.priority = priority;
        this.isStarred = isStarred;

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

    public void addTag(String tag) {

        if (tag == null || tag.equals(""))
            throw new IllegalArgumentException();

        notes.add(tag);
    }

    public void deleteTag(String tag) {

        if (tag == null)
            throw new IllegalArgumentException();

        notes.remove(tag);

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

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {

        if (tags == null)
            throw new IllegalArgumentException();

        this.tags = tags;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {

        if (taskID < 0)
            throw new IllegalArgumentException();

        this.taskID = taskID;

    }

    public boolean isStarred() {
        return isStarred;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    @Override
    public String toString() {

        StringBuilder notesString = new StringBuilder();
        for (int i = 0; i < notes.size(); i++) {
            notesString.append(String.format("%d- ", i + 1) + notes.get(i));

            if (i != notes.size() - 1)
                notesString.append('\n');

        }

        StringBuilder tagsString = new StringBuilder();
        for (int i = 0; i < tags.size(); i++) {
            tagsString.append(String.format("%d- ", i + 1) + tags.get(i));

            if (i != tags.size() - 1)
                tagsString.append('\n');

        }

        return "Task ID: " + taskID
                + "\n"
                + "Task title: " + title
                + "\n"
                + "Task datetime: " + dateTime.toString()
                + "\n"
                + "Task status: " + taskStatus.toString()
                + "\n"
                + "Task priority: " + priority.toString()
                + "\n"
                + "Task is starred: " + isStarred
                + "\n"
                + "Notes: \n" + notesString.toString()
                + "\n"
                + "Tags: \n" + tagsString.toString();

    }

}
