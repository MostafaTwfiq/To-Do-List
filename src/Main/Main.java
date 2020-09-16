package Main;

import DataBase.DataAccess;
import GUI.Screen.CustomStage;
import GUI.MultiProgressBar.MultiProgressBar;
import GUI.MultiProgressBar.MultiProgressElement;
import GUI.ProgressBar.ProgressBar;
import GUI.Screen.ScreenManager;
import DataClasses.Task;
import DataClasses.TaskStatus.TaskPriority;
import DataClasses.TaskStatus.TaskStatus;
import TasksListHandling.TasksListHandling;
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

        Task task = new Task(1, "title", new Vector<>(), new Vector<>(), "2020-06-02 15:05:56", TaskStatus.DONE, TaskPriority.IMPORTANT_AND_URGENT);

        Pane mainPane = new Pane();
        mainPane.styleProperty().set("-fx-background-color: transparent;");
        Label label1 = new Label(task.getDateTime().toString().substring(task.getDateTime().toString().indexOf('T') + 1));
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

        Vector<MultiProgressElement> elements = new Vector<>();
        elements.add(new MultiProgressElement(20, Color.rgb(98, 220, 165)));
        elements.add(new MultiProgressElement(30, Color.rgb(247, 248, 121)));
        elements.add(new MultiProgressElement(40, Color.rgb(230,126,34)));
        elements.add(new MultiProgressElement(10, Color.rgb(226, 103, 90)));


        ProgressBar progressBar = new ProgressBar(
                Color.rgb(149,165,166),
                Color.rgb(39,174,96),
                Color.rgb(236,240,241),
                700, 20, 300);

        progressBar.setLayoutX(400);
        progressBar.setLayoutY(400);

        progressBar.updateProgress(0.8);


        MultiProgressBar multiProgressBar = new MultiProgressBar(elements, 700, 5, 20, 300);
        multiProgressBar.setLayoutX(400);
        multiProgressBar.setLayoutY(500);
        mainPane.getChildren().addAll(multiProgressBar, progressBar);

        Button button1 = new Button();
        button1.setOnAction(e -> {
            screenManager.changeToLastScreen();
            multiProgressBar.updateProgress();
            progressBar.updateProgress(progressBar.getPercentage() + 0.3);
        });
        secondPane.getChildren().add(button1);

        CustomStage customStage = new CustomStage(700, 1000, mainPane);
        customStage.showStageAndWait();

    }




    public static void main(String[] args) {
        launch(args);
    }

}
