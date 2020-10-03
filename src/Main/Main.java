package Main;

import DataClasses.User;
import GUI.ScreenManager.Stage.CustomStage;
import GUI.ScreenManager.ScreenManager;
import GUI.Screens.LoginScreen.LoginScreenController;
import GUI.Style.Style.LightTheme;
import GUI.Style.Style.Style;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import GUI.Style.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

}
