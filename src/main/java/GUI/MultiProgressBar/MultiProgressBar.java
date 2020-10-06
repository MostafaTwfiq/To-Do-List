package GUI.MultiProgressBar;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;
import java.util.Vector;

public class MultiProgressBar extends HBox {

    List<MultiProgressElement> elements;
    double totalRatio;
    double updatingTime;

    public MultiProgressBar(Vector<MultiProgressElement> elements,
                            double updatingTime, double spacing, double h, double w) {

        if (elements == null || updatingTime < 0 || h < 0 || w < 0)
            throw new IllegalArgumentException();

        this.updatingTime = updatingTime;

        this.elements = elements;

        calculateTotalRatio();

        setMaxHeight(h);
        setMaxWidth(w);
        setPrefHeight(h);
        setPrefWidth(w);

        setSpacing(spacing);

        setupProgressBarStyle();

        updateProgress();

    }

    public MultiProgressBar(double updatingTime, double spacing, double h, double w) {

        if (updatingTime < 0 || h < 0 || w < 0)
            throw new IllegalArgumentException();

        this.updatingTime = updatingTime;

        this.elements = new Vector<>();

        calculateTotalRatio();

        setMaxHeight(h);
        setMaxWidth(w);
        setPrefHeight(h);
        setPrefWidth(w);
        setSpacing(spacing);

        setupProgressBarStyle();

    }

    private void setupProgressBarStyle() {

        styleProperty().set("-fx-background-color: transparent;"
                + "-fx-border-color: transparent;"
                + "-fx-background-radius: " + String.format("%f", getPrefHeight() / 2) + ";"
                + "-fx-border-radius: " + String.format("%f", getPrefHeight() / 2) + ";");

    }

    public void changeHeight(double h) {
        setMaxHeight(h);
        setPrefHeight(h);
        setupProgressBarStyle();
        updateProgress();
    }

    public void changeWidth(double w) {
        setMaxWidth(w);
        setPrefWidth(w);
        setupProgressBarStyle();
        updateProgress();
    }

    private void calculateTotalRatio() {

        totalRatio = 0;

        for (MultiProgressElement element : elements)
            totalRatio += element.getRatio();

    }

    public void addElement(MultiProgressElement element) {

        if (element == null)
            throw new IllegalArgumentException();

        totalRatio += element.getRatio();

        elements.add(element);

    }

    public void removeElement(MultiProgressElement element) {

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

        for (MultiProgressElement element : elements)
            getChildren().add(setupProgressRec(element, keyValues));

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(updatingTime), keyValues.toArray(new KeyValue[0])));
        timeline.play();

    }

    private Rectangle setupProgressRec(MultiProgressElement element, Vector<KeyValue> keyValues) {

        Rectangle rec = new Rectangle();

        rec.setFill(element.getColor());

        rec.setHeight(getPrefHeight());
        rec.setWidth(0);

        rec.setArcHeight(getPrefHeight() / 2);
        rec.setArcWidth(getPrefHeight() / 2);

        keyValues.add(new KeyValue(rec.widthProperty(),
                (element.getRatio() / totalRatio) * (getPrefWidth() - elements.size() * getSpacing()))
        );

        return rec;

    }

    public List<MultiProgressElement> getElements() {
        return elements;
    }

    public void setElements(List<MultiProgressElement> elements) {

        if (elements == null)
            throw new IllegalArgumentException();

        this.elements = elements;
        calculateTotalRatio();

    }

    public double getUpdatingTime() {
        return updatingTime;
    }

    public void setUpdatingTime(double updatingTime) {

        if (updatingTime < 0)
            throw new IllegalArgumentException();

        this.updatingTime = updatingTime;

    }

}
