package GUI.ScreenManager.Stage;

import GUI.IControllers;
import GUI.Observer.IObserver;
import GUI.ScreenManager.ScreenManager;
import GUI.Style.ScreensPaths;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.embed.swing.JFXPanel;
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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class CustomStage implements IObserver {

    private JFrame stage;
    private Scene scene;
    private final StageRootController rootController;
    private AnchorPane stageRoot;
    private final ScreenManager screenManager;
    private final int height, width;

    public CustomStage(int height, int width, IControllers mainLayoutController) {

        this.height = height;
        this.width = width;

        screenManager = Main.screenManager;
        Main.screenManager.addObserver(this);

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
        JFXPanel jfxPanel = new JFXPanel();
        jfxPanel.setScene(scene);
        SwingUtilities.invokeLater(() -> {
            stage.add(jfxPanel);
            stage.pack();
        });

        screenManager.changeScreen(mainLayoutController);

    }

    public void makeStageVisible() {
        stage.setVisible(true);
    }

    private void setupScene() {
        scene = new Scene(stageRoot, width, height);
        scene.setFill(Color.TRANSPARENT);

        scene.setOnDragDetected(e -> scene.startFullDrag());

    }

    private void setupStage() {
        stage = new JFrame();
        stage.setUndecorated(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        stage.setLocation(dim.width/2 - width/2, dim.height/2 - height/2);

        try {
            stage.setIconImage(ImageIO.read(getClass().getResource("/TodoAppIcon.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                        Duration.millis(300),
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
