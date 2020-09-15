package DataClasses;

import java.time.LocalDate;
import java.util.Vector;

public class User {

    private String userName;
    private short userID;
    private Vector<DayTasks> daysTasks;

    public User() {
        daysTasks = new Vector<>();
    }

    public User(String userName, short userID, Vector<DayTasks> daysTasks) {

        if (userName == null || daysTasks == null)
            throw new IllegalArgumentException();

        this.userName = userName;
        this.daysTasks = daysTasks;
        this.userID = userID;
    }

    public DayTasks getTasksAtDate(LocalDate date) {

        for (DayTasks currentDay : daysTasks) {

            if (currentDay.getDate().isEqual(date))
                return currentDay;

        }

        return null;

    }

    public void addDayTasks(DayTasks dayTasks) {

        if (dayTasks == null)
            throw new IllegalArgumentException();

        daysTasks.add(dayTasks);

    }

    public void removeDayTask(DayTasks dayTasks) {

        if (dayTasks == null)
            throw new IllegalArgumentException();

        daysTasks.remove(dayTasks);

    }

    public Vector<DayTasks> getDaysTasks() {
        return daysTasks;
    }

    public void setDaysTasks(Vector<DayTasks> daysTasks) {

        if (daysTasks == null)
            throw new IllegalArgumentException();

        this.daysTasks = daysTasks;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {

        if (userName == null)
            throw new IllegalArgumentException();

        this.userName = userName;

    }

    public short getUserID() {
        return userID;
    }

    public void setUserID(short userID) {

        if (userID < 0)
            throw new IllegalArgumentException();

        this.userID = userID;
    }

}