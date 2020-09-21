package Main;

import DataBase.DataAccess;
import DataClasses.User;
import GUI.ScreenManager.Stage.CustomStage;
import GUI.MultiProgressBar.MultiProgressBar;
import GUI.MultiProgressBar.MultiProgressElement;
import GUI.ProgressBar.ProgressBar;
import GUI.ScreenManager.ScreenManager;
import DataClasses.Task;
import DataClasses.TaskStatus.TaskPriority;
import DataClasses.TaskStatus.TaskStatus;
import GUI.Screens.LoginScreen.LoginScreenController;
import GUI.SearchBox.SearchBox;
import GUI.Style.Style;
import com.jfoenix.controls.JFXChipView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import GUI.Style.*;

import java.util.Vector;

public class Main extends Application {

    public static ScreenManager screenManager = new ScreenManager();
    public static User user;
    public static Style theme = new LightTheme();

    @Override
    public void start(Stage stage) throws Exception {

        LoginScreenController loginScreenController = new LoginScreenController();
        Parent loginScreenParent = null;

        try {

            ScreensPaths paths = new ScreensPaths();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.getLoginScreenFxml()));
            loader.setController(loginScreenController);
            loginScreenParent = loader.load();
            loginScreenParent.getStylesheets().add(paths.getLoginScreenCssSheet());

        } catch (Exception e) {
            e.printStackTrace();
        }

        CustomStage customStage = new CustomStage(700, 1000, loginScreenController);

        customStage.showStageAndWait();

    }


    public static void main(String[] args) {
        launch(args);
    }


    private static Parent tryPane() {
        Task task = new Task(1, "title", new Vector<>(), new Vector<>(), "2020-06-02 15:05:56", TaskStatus.DONE, TaskPriority.IMPORTANT_AND_URGENT, true);

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
            //screenManager.changeScreen(secondPane);
        });

        mainPane.getChildren().add(button);

        Vector<MultiProgressElement> elements = new Vector<>();
        elements.add(new MultiProgressElement(20, Color.rgb(98, 220, 165)));
        elements.add(new MultiProgressElement(30, Color.rgb(247, 248, 121)));
        elements.add(new MultiProgressElement(40, Color.rgb(230,126,34)));
        elements.add(new MultiProgressElement(10, Color.rgb(226, 103, 90)));


        ProgressBar progressBar = new ProgressBar(
                Color.rgb(149,165,166),
                Color.rgb(236,240,241),
                Color.rgb(0,0,0),
                700, 20, 300);

        progressBar.setLayoutX(400);
        progressBar.setLayoutY(400);

        progressBar.updateProgress(0.8);


        SearchBox searchBox = new SearchBox(20, 500, "Search",
                Color.rgb(236,240,241),
                Color.rgb(226, 103, 90),
                Color.rgb(149,165,166)
        );

        searchBox.setLayoutX(400);
        searchBox.setLayoutY(200);


        MultiProgressBar multiProgressBar = new MultiProgressBar(elements, 700, 5, 10, 300);
        multiProgressBar.setLayoutX(400);
        multiProgressBar.setLayoutY(500);

        JFXChipView<String> chipView = new JFXChipView<>();
        chipView.setStyle("-fx-background-color: red;");
        chipView.setMaxWidth(500);
        chipView.setMinWidth(500);
        chipView.setMaxHeight(500);
        chipView.setMinHeight(500);
        chipView.setLayoutX(200);
        chipView.setLayoutY(200);
        mainPane.getChildren().addAll(chipView);


        mainPane.getChildren().addAll(multiProgressBar, progressBar, searchBox);

        Button button1 = new Button();
        button1.setOnAction(e -> {
            screenManager.changeToLastScreen();
            multiProgressBar.updateProgress();
            progressBar.updateProgress(progressBar.getPercentage() + 0.3);
        });
        secondPane.getChildren().add(button1);

        return mainPane;

    }


}
