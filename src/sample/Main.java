package sample;

import Tasks.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Vector;

public class Main extends Application {

    double x, y;
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 1000, 800);
        stage.setScene(scene);
        //stage.setScene(new Scene(root, 300, 275));
        //primaryStage.show();

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y= event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
            stage.setOpacity(0.7f);
        });
        root.setOnDragDone((event) -> {
            stage.setOpacity(1.0f);
        });
        root.setOnMouseReleased((event) -> {
            stage.setOpacity(1.0f);
        });
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
