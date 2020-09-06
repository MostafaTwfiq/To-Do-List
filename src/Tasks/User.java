package Tasks;

import java.time.LocalDate;
import java.util.Vector;

public class User {

    private Vector<DayTasks> daysTasks;

    public User() {
        daysTasks = new Vector<>();
    }

    public User(Vector<DayTasks> daysTasks) {

        if (daysTasks == null)
            throw new IllegalArgumentException();

        this.daysTasks = daysTasks;
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
}
