package Main;

import DataClasses.User;
import GUI.ScreenManager.Stage.CustomStage;
import GUI.ScreenManager.ScreenManager;
import GUI.Screens.SplashScreen.SplashScreenController;
import GUI.Style.Style.LightTheme;
import GUI.Style.Style.Style;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import GUI.Style.*;

public class Main extends Application {

    public static ScreenManager screenManager = new ScreenManager();
    public static User user;
    public static Style theme = new LightTheme();

    @Override
    public void start(Stage stage) throws Exception {

        SplashScreenController splashScreenController = new SplashScreenController();

        try {

            ScreensPaths paths = new ScreensPaths();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.getSplashScreenFxml()));
            loader.setController(splashScreenController);
            loader.load();

        } catch (Exception e) {
            e.printStackTrace();
        }

        CustomStage customStage = new CustomStage(700, 1000, splashScreenController);
        customStage.makeStageVisible();
        splashScreenController.startSplashScreen();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
