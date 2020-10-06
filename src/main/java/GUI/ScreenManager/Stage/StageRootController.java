package GUI.ScreenManager.Stage;

import GUI.IControllers;
import GUI.Style.ScreensPaths;
import Main.Main;
import GUI.Style.ColorTransformer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
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

    private final JFrame stage;

    private String title;

    private EventHandler<ActionEvent> onClosingEvent;

    private void setupStageRoot() {
        stageRoot.setOnDragDetected(e -> stageRoot.startFullDrag());
    }

    private void setupParentRoot() {
        parentRoot.setOnDragDetected(e -> parentRoot.startFullDrag());
    }

    public StageRootController(JFrame stage, EventHandler<ActionEvent> onClosingEvent) {
        this.stage = stage;
        this.onClosingEvent = onClosingEvent;
        title = "";
    }

    public StageRootController(JFrame stage, String title, EventHandler<ActionEvent> onClosingEvent) {
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
            stage.setLocation(
                    (int) (event.getScreenX() - currentClickX),
                    (int) (event.getScreenY() - currentClickY)
            );
            stage.setOpacity(0.7f);
        });

        stageBar.setOnMouseReleased((event) -> {
            stage.setOpacity(1.0f);
        });
    }

    private void setupMinimizeB() {

        setMinimizeBStyle();

        minimizeB.setOnAction(e -> addAppToTray());

    }

    private void addAppToTray() {

        try {
            // ensure awt toolkit is initialized.
            java.awt.Toolkit.getDefaultToolkit();

            if (!java.awt.SystemTray.isSupported()) {
                stage.setState(Frame.ICONIFIED);
                return;
            }

            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();

            BufferedImage image = ImageIO.read(getClass().getResource("/TodoAppTrayIcon.png"));
            int trayIconWidth = new TrayIcon(image).getSize().width;
            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(
                    image.getScaledInstance(trayIconWidth, -1, java.awt.Image.SCALE_SMOOTH),
                    "Todo List"
            );

            trayIcon.addActionListener(event -> {
                SwingUtilities.invokeLater(()->{stage.setVisible(true);});
                tray.remove(trayIcon);
            });

            PopupMenu popupMenu = new PopupMenu();
            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(e -> closeB.fire());
            MenuItem minimizeItem = new MenuItem("Minimize");
            minimizeItem.addActionListener(e  -> {
                stage.setState(Frame.ICONIFIED);
                trayIcon.getActionListeners()[0].actionPerformed(e);
            });
            MenuItem maximizeItem = new MenuItem("Maximize");
            maximizeItem.addActionListener(e -> {
                trayIcon.getActionListeners()[0].actionPerformed(e);
            });

            popupMenu.add(maximizeItem);
            popupMenu.add(minimizeItem);
            popupMenu.add(exitItem);
            trayIcon.setPopupMenu(popupMenu);

            stage.setVisible(false);
            tray.add(trayIcon);

        } catch (AWTException | MalformedURLException e) {
            System.out.println("Unable to init system tray");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                            + new ColorTransformer().colorToHex(Main.theme.getStageStyle().getMinimizeBOnMoveColor())
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
                    + new ColorTransformer().colorToHex(Main.theme.getStageStyle().getCloseBOnMoveColor())
                    + ";"
            );

        });

        closeB.setOnMouseExited( e-> {

            closeB.styleProperty().setValue("-fx-background-color: transparent;");

        });

    }


    private ImageView loadButtonImage(String imagePath, double h, double w) {

        try {

            Image buttonImage = new Image(imagePath);
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
