package GUI.Style.Style.ExtraComponents;

import GUI.MultiProgressBar.MultiProgressBar;
import javafx.scene.paint.Color;

public class MultiProgressBarTheme {

    private final Color priority1;
    private final Color priority2;
    private final Color priority3;
    private final Color priority4;

    public MultiProgressBarTheme(Color priority1, Color priority2, Color priority3, Color priority4) {
        this.priority1 = priority1;
        this.priority2 = priority2;
        this.priority3 = priority3;
        this.priority4 = priority4;
    }

    public Color getPriority1() {
        return priority1;
    }

    public Color getPriority2() {
        return priority2;
    }

    public Color getPriority3() {
        return priority3;
    }

    public Color getPriority4() {
        return priority4;
    }

}
