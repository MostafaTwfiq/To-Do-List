package sample;

import GUI.CustomStage;
import GUI.ScreenManager;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static ScreenManager screenManager = new ScreenManager();

    double x, y;
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

        Button button1 = new Button();
        button1.setOnAction(e -> {
            screenManager.changeToLastScreen();
        });
        secondPane.getChildren().add(button1);

        CustomStage customStage = new CustomStage(700, 1000, mainPane);
        customStage.showStageAndWait();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
