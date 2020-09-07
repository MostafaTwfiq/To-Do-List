package GUI.ProgressBar;

import javafx.scene.paint.Color;

public class ProgressElement {

    private double ratio;
    private Color color;

    public ProgressElement(double ratio, Color color) {

        if (ratio < 0 || color == null)
            throw new IllegalArgumentException();

        this.ratio = ratio;
        this.color = color;

    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {

        if (ratio < 0)
            throw new IllegalArgumentException();

        this.ratio = ratio;

    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {

        if (color == null)
            throw new IllegalArgumentException();

        this.color = color;
    }

}
