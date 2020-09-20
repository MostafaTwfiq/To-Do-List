package GUI.Screens.SignUpScreen;

import DataBase.DataAccess;
import Main.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SignUpScreenController implements Initializable {

    @FXML
    private AnchorPane signUpPane;

    @FXML
    private ImageView userImageV;

    @FXML
    private Button chooseImageB;

    @FXML
    private JFXTextField userNameTF;

    @FXML
    private JFXButton cancelB;

    @FXML
    private JFXButton signUpB;

    @FXML
    private JFXPasswordField passwordTF;

    @FXML
    private Label errorL;

    private String userImagePath;

    private DataAccess dataAccess;

    private final String imageExtensionsRegex = "^\\.(jpe?g|png|gif|bmp)$";

    public SignUpScreenController() throws SQLException {
        dataAccess = new DataAccess();
        userImagePath = null;
    }

    private void setupUserImageV() {

        try {
            FileInputStream fileInputStream = new FileInputStream("resources/Users/defaultUserProfileImage.png");
            Image image = new Image(fileInputStream);
            userImageV.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setupSignUpPane() {

        signUpPane.setOnDragEntered(e -> signUpPane.setOpacity(0.5));
        signUpPane.setOnDragExited(e -> signUpPane.setOpacity(1));
        signUpPane.setOnDragOver(new EventHandler<>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getDragboard().hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                    updateUserImageFile(event.getDragboard().getFiles().get(0));
                }
                event.consume();
            }
        });

    }


    private void setupChooseImageB() {

        try {

            FileInputStream imageStream = new FileInputStream("resources/Users/chooseImage.png");

            Image buttonImage = new Image(imageStream);
            ImageView buttonImageView = new ImageView(buttonImage);
            buttonImageView.setFitHeight(35);
            buttonImageView.setFitWidth(40);

            chooseImageB.setGraphic(buttonImageView);

        } catch (Exception e) {
            System.out.println("There is an error happened while loading images.");
        }

        chooseImageB.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(new Stage());

            updateUserImageFile(file);

        });

    }

    private void updateUserImageFile(File file) {
        if (file != null) {

            String filePath = file.getPath();
            String fileExtension = filePath.substring(filePath.lastIndexOf('.'));
            if (!Pattern.matches(imageExtensionsRegex, fileExtension))
                return;

            userImagePath = filePath;

            try {
                FileInputStream fileInputStream = new FileInputStream(userImagePath);
                Image image = new Image(fileInputStream);
                userImageV.setImage(image);
            } catch (Exception exception) {
                userImagePath = null;
            }

        }
    }

    private void setupSignUpB() {

        signUpB.setOnAction(e -> {

            if (userNameTF.getText().equals("") || passwordTF.getText().equals("")) {
                errorL.setText("Please enter a valid user name and password.");
                return;
            }

            lockSignUpScreen();

            try {

                if (dataAccess.checkIfUserNameExists(userNameTF.getText())){
                    errorL.setText("This user name already exits.");
                }  else {

                    dataAccess.addNewUser(userNameTF.getText(), passwordTF.getText());

                    if (userImagePath != null)
                        dataAccess.addNewUserImage(dataAccess.getLastInsertedID(), userImagePath);

                    TrayNotification notification = new TrayNotification();
                    notification.setTitle("New user has been added successfully");
                    notification.setMessage("Welcome " + userNameTF.getText());
                    notification.setAnimationType(AnimationType.POPUP);
                    notification.setNotificationType(NotificationType.SUCCESS);
                    notification.showAndWait();

                    Main.screenManager.changeToLastScreen();

                }

            } catch (SQLException sqlException) {
                errorL.setText("Something wrong happened please try again.");
                sqlException.printStackTrace();
            }

            unlockSignUpScreen();

        });

    }

    private void setupCancelB() {

        cancelB.setOnAction(e -> {
            Main.screenManager.changeToLastScreen();
        });

    }


    private void setupUserNameTF() {

        userNameTF.setOnKeyReleased(e -> {

            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.DOWN)
                passwordTF.requestFocus();

        });

    }

    private void setupPasswordTF() {

        passwordTF.setOnKeyReleased(e -> {

            if (e.getCode() == KeyCode.ENTER)
                signUpB.fire();
            else if (e.getCode() == KeyCode.UP)
                userNameTF.requestFocus();

        });

    }

    private void lockSignUpScreen() {
        userNameTF.setDisable(true);
        passwordTF.setDisable(true);
        signUpB.setDisable(true);
        cancelB.setDisable(true);
    }

    private void unlockSignUpScreen() {
        userNameTF.setDisable(false);
        passwordTF.setDisable(false);
        signUpB.setDisable(false);
        cancelB.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupSignUpPane();
        setupUserImageV();
        setupSignUpB();
        setupChooseImageB();
        setupCancelB();
        setupUserNameTF();
        setupPasswordTF();
    }

}
