package GUI.ProgressBar;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Vector;

public class ProgressBar extends HBox {

    Vector<ProgressElement> elements;
    double totalRatio;
    double updatingTime;

    public ProgressBar(Vector<ProgressElement> elements, double updatingTime, double spacing, double h, double w) {

        if (elements == null)
            throw new IllegalArgumentException();

        this.updatingTime = updatingTime;

        this.elements = elements;

        calculateTotalRatio();

        setPrefHeight(h);
        setPrefWidth(w);
        setSpacing(spacing);

        updateProgress();

    }

    private void setupProgressBarStyle() {

        styleProperty().set("-fx-background-color: #2e2d34;"
                + "-fx-border-color: transparent;"
                + "-fx-background-radius: " + String.format("%f", getPrefHeight() / 2) + ";"
                + "-fx-border-radius: " + String.format("%f", getPrefHeight() / 2) + ";");

    }

    public void changeHeight(double h) {
        setPrefHeight(h);
        setupProgressBarStyle();
        updateProgress();
    }

    public void changeWidth(double w) {
        setPrefWidth(w);
        setupProgressBarStyle();
        updateProgress();
    }

    private void calculateTotalRatio() {

        totalRatio = 0;

        for (ProgressElement element : elements)
            totalRatio += element.getRatio();

    }

    public void addElement(ProgressElement element) {

        if (element == null)
            throw new IllegalArgumentException();

        totalRatio += element.getRatio();

        elements.add(element);

    }

    public void removeElement(ProgressElement element) {

        if (element == null)
            return;

        if (elements.contains(element)) {
            totalRatio -= element.getRatio();
            elements.remove(element);
        }

    }

    public void updateProgress() {

        getChildren().clear();

        Vector<KeyValue> keyValues = new Vector<>();

        for (ProgressElement element : elements)
            getChildren().add(setupProgressRec(element, keyValues));

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(updatingTime), keyValues.toArray(new KeyValue[0])));
        timeline.play();

    }

    private Rectangle setupProgressRec(ProgressElement element, Vector<KeyValue> keyValues) {

        Rectangle rec = new Rectangle();

        rec.setFill(element.getColor());

        rec.setHeight(getPrefHeight());
        rec.setWidth(0);

        rec.setArcHeight(getPrefHeight() / 2);
        rec.setArcWidth(getPrefHeight() / 2);

        keyValues.add(new KeyValue(rec.widthProperty(), (element.getRatio() / totalRatio) * getPrefWidth()));

        return rec;

    }

    public Vector<ProgressElement> getElements() {
        return elements;
    }

    public void setElements(Vector<ProgressElement> elements) {

        if (elements == null)
            throw new IllegalArgumentException();

        this.elements = elements;
        calculateTotalRatio();

    }

}
