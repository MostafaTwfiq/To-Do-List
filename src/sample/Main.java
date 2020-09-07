package sample;

import GUI.CustomStage;
import GUI.ProgressBar.ProgressBar;
import GUI.ProgressBar.ProgressElement;
import GUI.ScreenManager;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Vector;

public class Main extends Application {

    public static ScreenManager screenManager = new ScreenManager();

    @Override
    public void start(Stage stage) throws Exception{

        Pane mainPane = new Pane();
        mainPane.styleProperty().set("-fx-background-color: transparent;");
        Label label1 = new Label("First layout");
        label1.setLayoutX(200);
        label1.setLayoutY(200);
        label1.setStyle("-fx-text-fill: white;");
        mainPane.getChildren().add(label1);

        Button button = new Button();
        Pane secondPane = new Pane();
        secondPane.styleProperty().set("-fx-background-color: transparent;");
        Label label2 = new Label("Second layout");
        label2.setLayoutX(200);
        label2.setLayoutY(200);
        label2.setStyle("-fx-text-fill: white;");
        secondPane.getChildren().add(label2);

        button.setOnAction(e -> {
            screenManager.changeScreen(secondPane);
        });

        mainPane.getChildren().add(button);

        Vector<ProgressElement> elements = new Vector<>();
        elements.add(new ProgressElement(50, Color.rgb(98, 220, 165)));
        elements.add(new ProgressElement(20, Color.rgb(247, 248, 121)));
        elements.add(new ProgressElement(30, Color.rgb(226, 103, 90)));


        ProgressBar progressBar = new ProgressBar(elements, 500, 2, 15, 400);
        progressBar.setLayoutX(500);
        progressBar.setLayoutY(500);
        mainPane.getChildren().add(progressBar);

        Button button1 = new Button();
        button1.setOnAction(e -> {
            screenManager.changeToLastScreen();
            progressBar.updateProgress();
        });
        secondPane.getChildren().add(button1);

        CustomStage customStage = new CustomStage(700, 1000, mainPane);
        customStage.showStageAndWait();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
