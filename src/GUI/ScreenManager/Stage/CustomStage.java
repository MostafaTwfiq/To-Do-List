package GUI.ScreenManager.Stage;

import GUI.IControllers;
import GUI.Observer.IObserver;
import GUI.ScreenManager.ScreenManager;
import GUI.Style.ScreensPaths;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import Main.Main;

public class CustomStage implements IObserver {

    private Stage stage;
    private Scene scene;
    private StageRootController rootController;
    private AnchorPane stageRoot;
    private ScreenManager screenManager;
    private int height, width;

    public CustomStage(int height, int width, IControllers mainLayoutController) {

        this.height = height;
        this.width = width;

        screenManager = Main.screenManager;
        screenManager.addObserver(this);

        setupStage();
        rootController = new StageRootController(stage, "TODO", e -> System.exit(0));

        try {
            ScreensPaths paths = new ScreensPaths();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.getStageFxml()));
            loader.setController(rootController);
            stageRoot = loader.load();
            stageRoot.getStylesheets().add(paths.getStageCssSheet());
        } catch (Exception e) {
            e.printStackTrace();
        }


        setupScene();
        stage.setScene(scene);

        screenManager.changeScreen(mainLayoutController);

    }

    public void showStage() {
        stage.show();
    }

    public void showStageAndWait() {
        stage.showAndWait();
    }

    private void setupScene() {
        scene = new Scene(stageRoot, width, height);
        scene.setFill(Color.TRANSPARENT);

        scene.setOnDragDetected(e -> scene.startFullDrag());

    }

    private void setupStage() {

        stage = new Stage();

        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);

    }

    @Override
    public void update() {

        AnchorPane parent = rootController.getParentRoot();

        Parent newLayout = screenManager.getCurrentLayout();
        if (newLayout == null) {
            parent.getChildren().clear();
            return;
        }

        AnchorPane.setTopAnchor(newLayout, 0.0);
        AnchorPane.setBottomAnchor(newLayout, 0.0);
        AnchorPane.setLeftAnchor(newLayout, 0.0);
        AnchorPane.setRightAnchor(newLayout, 0.0);

        if (parent.getChildren().size() == 0) {
            parent.getChildren().add(newLayout);
            return;
        }
        Parent oldLayout = (Parent) parent.getChildren().get(0);

        createScreenChangeAnimation(parent, oldLayout, newLayout);


    }

    private void createScreenChangeAnimation(AnchorPane parent, Parent oldLayout, Parent newLayout) {

        newLayout.setOpacity(0);
        parent.getChildren().add(newLayout);

        KeyValue currentLayoutFadingKV = new KeyValue(oldLayout.opacityProperty(), 0);
        KeyValue newLayoutAppearanceKV = new KeyValue(newLayout.opacityProperty(), 1);
        screenManager.setLockScreen(true);

        Timeline changingLayoutTimeLine = new Timeline(
                new KeyFrame(
                        Duration.millis(200),
                        currentLayoutFadingKV,
                        newLayoutAppearanceKV));


        changingLayoutTimeLine.setOnFinished(e -> {

            parent.getChildren().remove(oldLayout);
            screenManager.setLockScreen(false);

        });

        changingLayoutTimeLine.play();

    }

    @Override
    public void updateStyle() {
        rootController.updateStyle();
    }

}
