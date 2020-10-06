package GUI.Screens.SplashScreen;


import GUI.IControllers;
import GUI.Screens.LoginScreen.LoginScreenController;
import GUI.Style.ScreensPaths;
import Main.Main;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements IControllers {

    @FXML
    private AnchorPane parentLayout;

    @FXML
    private ImageView appLogo;

    private Timeline resizingTimeLine;

    private Timeline reverseResizingTimeLine;

    private Timeline fadingTimeLine;

    public SplashScreenController() {}

    private void setupAppLogo() {
        try {
            appLogo.setImage(new Image("/TodoAppIcon.png"));
        } catch (Exception e) {
            System.out.println("Something went wrong while loading app logo in opening screen.");
        }
    }

    public void setupAnimationTimers() {
        double originalHeight = appLogo.getFitHeight();
        double originalWidth = appLogo.getFitWidth();

        appLogo.setFitHeight(0);
        appLogo.setFitWidth(0);

        KeyValue resizingHeightKV = new KeyValue(appLogo.fitHeightProperty(), originalHeight * 1.5);
        KeyValue resizingWidthKV = new KeyValue(appLogo.fitWidthProperty(), originalWidth * 1.5);
        KeyValue rotationKV = new KeyValue(appLogo.rotateProperty(), 360);

        KeyValue reverseResizingHeightKV = new KeyValue(appLogo.fitHeightProperty(), originalHeight);
        KeyValue reverseResizingWidthKV = new KeyValue(appLogo.fitWidthProperty(), originalWidth);

        resizingTimeLine = new Timeline(
                new KeyFrame(
                        Duration.millis(700),
                        resizingHeightKV, resizingWidthKV, rotationKV,
                        new KeyValue(appLogo.layoutXProperty(), parentLayout.getPrefWidth() / 2.0 - (originalWidth * 1.5) / 2.0),
                        new KeyValue(appLogo.layoutYProperty(), parentLayout.getPrefHeight() / 2.0 - (originalHeight * 1.5) / 2.0)
                )
        );

        reverseResizingTimeLine = new Timeline(
                new KeyFrame(
                        Duration.millis(300),
                        reverseResizingHeightKV, reverseResizingWidthKV,
                        new KeyValue(appLogo.layoutXProperty(), parentLayout.getPrefWidth() / 2.0 - originalWidth / 2.0),
                        new KeyValue(appLogo.layoutYProperty(), parentLayout.getPrefHeight() / 2.0 - originalHeight / 2.0)
                )
        );

        fadingTimeLine = new Timeline(
                new KeyFrame(
                        Duration.millis(700),
                        new KeyValue(appLogo.opacityProperty(), 0)
                )
        );

        fadingTimeLine.setOnFinished(e -> {

            try {

                LoginScreenController loginScreenController = new LoginScreenController();
                Parent loginScreenParent = null;

                ScreensPaths paths = new ScreensPaths();
                FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.getLoginScreenFxml()));
                loader.setController(loginScreenController);
                loginScreenParent = loader.load();
                loginScreenParent.getStylesheets().add(paths.getLoginScreenCssSheet());

                Main.screenManager.changeScreen(loginScreenController);

            } catch (Exception exception) {
                exception.printStackTrace();
            }

        });

        reverseResizingTimeLine.setOnFinished(e -> {
            fadingTimeLine.play();
        });

        resizingTimeLine.setOnFinished(e -> {
            reverseResizingTimeLine.play();
        });

    }

    public void startSplashScreen() {
        resizingTimeLine.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupAppLogo();
        setupAnimationTimers();
    }

    @Override
    public void updateStyle() {
    }

    @Override
    public Parent getParent() {
        return parentLayout;
    }
}
