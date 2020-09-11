package Tasks;

public enum TaskPriority {

    IMPORTANT_AND_URGENT(""), IMPORTANT_ANT_NOT_URGENT(""),
    NOT_IMPORTANT_AND_URGENT(""), NOT_IMPORTANT_AND_NOT_URGENT("");

    private TaskPriority(String color) {
        this.color = color;
    }

    private final String color;

    public String getColor() {
        return color;
    }

}
