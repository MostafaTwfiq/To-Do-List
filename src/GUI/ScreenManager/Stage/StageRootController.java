package GUI.ScreenManager.Stage;

import GUI.IControllers;
import GUI.Style.ScreensPaths;
import Main.Main;
import GUI.Style.ColorHandling;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class StageRootController implements IControllers {


    @FXML
    private AnchorPane stageRoot;

    @FXML
    private AnchorPane stageBar;

    @FXML
    private Button closeB;

    @FXML
    private Button minimizeB;

    @FXML
    private Label titleL;

    @FXML
    private AnchorPane parentRoot;

    private Stage stage;

    private String title;

    private EventHandler<ActionEvent> onClosingEvent;

    private void setupStageRoot() {
        stageRoot.setOnDragDetected(e -> stageRoot.startFullDrag());
    }

    private void setupParentRoot() {
        parentRoot.setOnDragDetected(e -> parentRoot.startFullDrag());
    }

    public StageRootController(Stage stage, EventHandler<ActionEvent> onClosingEvent) {
        this.stage = stage;
        this.onClosingEvent = onClosingEvent;
        title = "";
    }

    public StageRootController(Stage stage, String title, EventHandler<ActionEvent> onClosingEvent) {
        this.stage = stage;
        this.onClosingEvent = onClosingEvent;
        this.title = title;
    }

    private double currentClickX, currentClickY;

    private void setupStageBar() {

        stageBar.setOnMousePressed(event -> {
            currentClickX = event.getSceneX();
            currentClickY = event.getSceneY();
        });

        stageBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - currentClickX);
            stage.setY(event.getScreenY() - currentClickY);
            stage.setOpacity(0.7f);
        });

        stageBar.setOnMouseReleased((event) -> {
            stage.setOpacity(1.0f);
        });
    }

    private void setupMinimizeB() {

        setMinimizeBStyle();

        minimizeB.setOnAction(e -> stage.setIconified(true));

    }

    private void setMinimizeBStyle() {

        minimizeB.setGraphic(
                loadButtonImage(
                        Main.theme.getThemeResourcesPath() + "Stage/minimizeB.png",
                        15, 15
                )
        );

        minimizeB.setOnMouseEntered(e -> {
            minimizeB.styleProperty().setValue(
                    "-fx-background-color: "
                            + new ColorHandling().colorToHex(Main.theme.getStageStyle().getMinimizeBOnMoveColor())
                            + ";"
            );

        });
        minimizeB.setOnMouseExited( e-> {
            minimizeB.styleProperty().setValue("-fx-background-color: transparent;");
        });

    }

    private void setupCloseB() {

        setCloseBStyle();

        closeB.setOnAction(onClosingEvent);
    }

    private void setCloseBStyle() {

        closeB.setGraphic(
                loadButtonImage(
                Main.theme.getThemeResourcesPath() + "Stage/closeB.png",
                15, 15
                )
        );

        closeB.setOnMouseEntered(e -> {

            closeB.styleProperty().setValue("-fx-background-color: "
                    + new ColorHandling().colorToHex(Main.theme.getStageStyle().getCloseBOnMoveColor())
                    + ";"
            );

        });

        closeB.setOnMouseExited( e-> {

            closeB.styleProperty().setValue("-fx-background-color: transparent;");

        });

    }


    private ImageView loadButtonImage(String imagePath, double h, double w) {

        try {
            FileInputStream imageStream = new FileInputStream(imagePath);

            Image buttonImage = new Image(imageStream);
            ImageView buttonImageView = new ImageView(buttonImage);
            buttonImageView.setFitHeight(h);
            buttonImageView.setFitWidth(w);

            return buttonImageView;

        } catch (Exception e) {
            System.out.println("There is an error happened while loading buttons images in stage.");
        }

        return new ImageView();

    }

    public void setStageTitle(String title) {
        titleL.setText(title);
    }

    public AnchorPane getStageRoot() {
        return stageRoot;
    }

    public AnchorPane getStageBar() {
        return stageBar;
    }

    public Button getCloseB() {
        return closeB;
    }

    public Button getMinimizeB() {
        return minimizeB;
    }

    public Label getTitleL() {
        return titleL;
    }

    public AnchorPane getParentRoot() {
        return parentRoot;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupStageRoot();
        setupParentRoot();
        setupStageBar();
        setupMinimizeB();
        setupCloseB();
        setStageTitle(title);
    }


    @Override
    public void updateStyle() {
        stageRoot.getStylesheets().clear();
        stageRoot.getStylesheets().add(new ScreensPaths().getStageCssSheet());
        setCloseBStyle();
        setMinimizeBStyle();
    }

    @Override
    public Parent getParent() {
        return stageRoot;
    }

}
