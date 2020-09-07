package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StageRootController implements Initializable {


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

        minimizeB.setOnMouseEntered(e -> {
            minimizeB.setEffect(new Glow(1));
        });
        minimizeB.setOnMouseExited( e-> {
            minimizeB.setEffect(null);
        });


        minimizeB.setOnAction(e -> stage.setIconified(true));

    }

    private void setupCloseB() {

        closeB.setOnMouseEntered(e -> {
            closeB.setEffect(new Glow(0.5));
        });
        closeB.setOnMouseExited( e-> {
            closeB.setEffect(null);
        });

        closeB.setOnAction(onClosingEvent);
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
        setupStageBar();
        setupMinimizeB();
        setupCloseB();
        setStageTitle(title);
    }

}
