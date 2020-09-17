package TasksListHandling;

import DataClasses.Task;
import DataClasses.TaskStatus.TaskPriority;
import DataClasses.TaskStatus.TaskStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TasksListHandling {

    public List<Integer> getAllTasksStatusNumInOrder(List<Task> tasks) {

        TaskStatus[] statuses = TaskStatus.values();

        List<Integer> counts = new ArrayList<>(statuses.length);
        for (int i = 0; i < statuses.length; i++)
            counts.add(0);

        for (Task task : tasks) {

            for (int i = 0; i < statuses.length; i++) {

                if (task.getTaskStatus() == statuses[i]) {
                    counts.set(i, counts.get(i) + 1);
                    break;
                }

            }

        }

        return counts;

    }

    public List<Double> getAllTasksStatusRatioInOrder(List<Task> tasks) {

        List<Integer> counts = getAllTasksStatusNumInOrder(tasks);

        List<Double> ratios = new ArrayList<>(counts.size());

        for (int i : counts)
            ratios.add( i / (tasks.size() * 1.0) );

        return ratios;

    }


    public List<Integer> getAllTasksPriorityNumInOrder(List<Task> tasks) {

        TaskPriority[] priorities = TaskPriority.values();

        List<Integer> counts = new ArrayList<>(priorities.length);
        for (int i = 0; i < priorities.length; i++)
            counts.add(0);

        for (Task task : tasks) {

            for (int i = 0; i < priorities.length; i++) {

                if (task.getPriority() == priorities[i]) {
                    counts.set(i, counts.get(i) + 1);
                    break;
                }

            }

        }

        return counts;

    }

    public List<Double> getAllTasksPriorityRatioInOrder(List<Task> tasks) {

        List<Integer> counts = getAllTasksPriorityNumInOrder(tasks);

        List<Double> ratios = new ArrayList<>(counts.size());

        for (int i : counts)
            ratios.add( i / (tasks.size() * 1.0) );

        return ratios;

    }


    public int getTasksStatusNum(List<Task> tasks, TaskStatus status) {

        int num = 0;

        for (Task task : tasks)
            num += task.getTaskStatus().toString().equals(status.toString()) ? 1 : 0;

        return num;

    }

    public double getTasksStatusRatio(List<Task> tasks, TaskStatus status) {
        return getTasksStatusNum(tasks, status) / (tasks.size() * 1.0);
    }

    public int getTasksPriorityNum(List<Task> tasks, TaskPriority priority) {

        int num = 0;

        for (Task task : tasks)
            num += task.getTaskStatus().toString().equals(priority.toString()) ? 1 : 0;

        return num;

    }

    public double getTasksPriorityRatio(List<Task> tasks, TaskPriority priority) {
        return getTasksPriorityNum(tasks, priority) / (tasks.size() * 1.0);
    }


    public List<Task> filterTasksByStatus(List<Task> tasks, TaskStatus status) {

        List<Task> filteredTasks = new ArrayList<>();

        for (Task task : tasks) {

            if (task.getTaskStatus() == status)
                filteredTasks.add(task);

        }

        return filteredTasks;

    }


    public List<Task> filterTasksByPriority(List<Task> tasks, TaskPriority priority) {

        List<Task> filteredTasks = new ArrayList<>();

        for (Task task : tasks) {

            if (task.getPriority() == priority)
                filteredTasks.add(task);

        }

        return filteredTasks;

    }



    public void sortTasksByDate(List<Task> tasks, boolean decs) {

        tasks.sort(new Comparator<>() {
            @Override
            public int compare(Task t1, Task t2) {

                String fDate = t1.getDateTime().toString();
                String sDate = t2.getDateTime().toString();

                int smallestLength = Math.min(fDate.length(), sDate.length());

                for (int i = 0; i < smallestLength; i++) {

                    if (fDate.charAt(i) != sDate.charAt(i))
                        return decs ? sDate.charAt(i) - fDate.charAt(i) : fDate.charAt(i) - sDate.charAt(i);

                }

                if (decs)
                    return sDate.length() < fDate.length() ? -1 : 1;
                else
                    return fDate.length() < sDate.length() ? -1 : 1;

            }

        });

    }

    public void sortTasksPriority(List<Task> tasks, boolean decs) {

        tasks.sort(new Comparator<>() {
            @Override
            public int compare(Task t1, Task t2) {

                if (decs)
                    return t2.getPriority().getPriorityWeight() - t1.getPriority().getPriorityWeight();
                else
                    return t1.getPriority().getPriorityWeight() - t2.getPriority().getPriorityWeight();

            }

        });

    }

}
