package GUI.Style.Style.ExtraComponents;

import javafx.scene.paint.Color;

public class ProgressBarTheme {

    private final Color progressBarC;
    private final Color progressC;
    private final Color labelC;

    public ProgressBarTheme(Color progressBarC, Color progressC, Color labelC) {
        this.progressBarC = progressBarC;
        this.progressC = progressC;
        this.labelC = labelC;
    }

    public Color getProgressBarC() {
        return progressBarC;
    }

    public Color getProgressC() {
        return progressC;
    }

    public Color getLabelC() {
        return labelC;
    }

}
