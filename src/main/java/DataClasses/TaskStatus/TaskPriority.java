package DataClasses.TaskStatus;

public enum TaskPriority {

    IMPORTANT_AND_URGENT(4), IMPORTANT_AND_NOT_URGENT(3),
    NOT_IMPORTANT_AND_URGENT(2), NOT_IMPORTANT_AND_NOT_URGENT(1);

    private TaskPriority(int priorityWeight) {
        this.priorityWeight = priorityWeight;
    }

    private int priorityWeight;

    public int getPriorityWeight() {
        return priorityWeight;
    }

}
