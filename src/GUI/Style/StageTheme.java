package GUI.Style;

import javafx.scene.paint.Color;

public class StageTheme {

    private final Color closeBOnMoveColor;
    private final Color minimizeBOnMoveColor;

    public StageTheme(Color closeBOnMoveColor, Color minimizeBOnMoveColor) {
        this.closeBOnMoveColor = closeBOnMoveColor;
        this.minimizeBOnMoveColor = minimizeBOnMoveColor;
    }

    public Color getCloseBOnMoveColor() {
        return closeBOnMoveColor;
    }

    public Color getMinimizeBOnMoveColor() {
        return minimizeBOnMoveColor;
    }

}
