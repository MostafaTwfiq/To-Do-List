package GUI.ProgressBar;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ProgressBar extends AnchorPane {

    private Pane progressP;
    private Label progressLabel;
    private double percentage;
    private double animationTimeM;
    private Color progressBarC;
    private Color progressC;
    private Color labelC;

    public ProgressBar(Color progressBarC, Color progressC, Color labelC, double animationTimeM, double height, double width) {

        this.progressBarC = progressBarC;
        this.progressC = progressC;
        this.labelC = labelC;
        this.animationTimeM = animationTimeM;

        percentage = 0;

        setupProgressBar(height, width);

        setupProgressP();
        setupProgressLabel();

        getChildren().addAll(progressP, progressLabel);

    }

    private void setupProgressBar(double height, double width) {

        setPrefHeight(height);
        setPrefWidth(width);

        setupProgressBarStyle();

    }


    private void setupProgressP() {

        progressP = new Pane();

        progressP.setPrefHeight(getPrefHeight());
        progressP.setPrefWidth(percentage * getPrefWidth());

        setupProgressPStyle();

        AnchorPane.setTopAnchor(progressP, 0.0);
        AnchorPane.setBottomAnchor(progressP, 0.0);
        AnchorPane.setLeftAnchor(progressP, 0.0);

    }

    private void setupProgressPStyle() {

        progressP.styleProperty().set("-fx-background-color: " + colorToHex(progressC) + ";"
                + "-fx-background-radius: " + String.format("%f", getPrefHeight() / 7.0)+ ";"
                + "-fx-border-color: transparent;"
                + "-fx-border-radius: " + String.format("%f", getPrefHeight() / 7.0)+ ";");
    }

    private void setupProgressLabel() {

        progressLabel = new Label(String.format("%.2f%c", percentage * 100, '%'));
        setupProgressLStyle();

        progressLabel.setAlignment(Pos.CENTER);

        AnchorPane.setTopAnchor(progressLabel, 0.0);
        AnchorPane.setBottomAnchor(progressLabel, 0.0);
        AnchorPane.setRightAnchor(progressLabel, 0.0);
        AnchorPane.setLeftAnchor(progressLabel, 0.0);

    }

    private void setupProgressLStyle() {

        progressLabel.setStyle("-fx-text-fill: " + colorToHex(labelC) + ";"
                + "-fx-font-size: " + String.format("%f", getPrefHeight() * 0.6)+ "px;"
                + "-fx-font-weight: bold;");

    }


    public void changeHeight(double h) {

        setPrefHeight(h);
        setupProgressBarStyle();

    }

    public void changeWidth(double w) {

        setPrefWidth(w);

    }

    private void setupProgressBarStyle() {

        styleProperty().set("-fx-background-color: " + colorToHex(progressBarC) + ";"
                + "-fx-background-radius: " + String.format("%f", getPrefHeight() / 7.0) + ";"
                + "-fx-border-radius: " + String.format("%f", getPrefHeight() / 7) + ";");

    }

    private String colorToHex(Color color) {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }

    public void updateProgress(double percentage) {

        if (percentage < 0)
            throw new IllegalArgumentException();

        else if (percentage > 1)
            percentage = 1;

        //progressP.setPrefWidth(0);
        Timeline progressShrinkingTL = new Timeline(
                new KeyFrame(
                        Duration.millis(animationTimeM),
                        new KeyValue(progressP.prefWidthProperty(), percentage * getPrefWidth())
                )
        );

        progressShrinkingTL.play();

        progressLabel.setText(String.format("%.2f%c", percentage * 100, '%'));

        this.percentage = percentage;

    }

    public double getPercentage() {
        return percentage;
    }

    public Color getProgressBarC() {
        return progressBarC;
    }

    public void setProgressBarC(Color progressBarC) {

        if (progressBarC == null)
            throw new IllegalArgumentException();

        this.progressBarC = progressBarC;

        setupProgressBarStyle();

    }

    public Color getProgressC() {
        return progressC;
    }

    public void setProgressC(Color progressC) {

        if (progressC == null)
            throw new IllegalArgumentException();

        this.progressC = progressC;

        setupProgressPStyle();

    }

    public Color getLabelC() {
        return labelC;
    }

    public void setLabelC(Color labelC) {

        if (labelC == null)
            throw new IllegalArgumentException();

        this.labelC = labelC;

        setupProgressLStyle();

    }
}
