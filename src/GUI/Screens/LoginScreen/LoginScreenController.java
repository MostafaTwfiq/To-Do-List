package GUI.Screens.LoginScreen;

import DataBase.DataAccess;
import DataClasses.User;
import Main.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {


    @FXML
    private AnchorPane loginPane;

    @FXML
    private Label welcomeL;

    @FXML
    private Label infoL;

    @FXML
    private JFXTextField userNameTF;

    @FXML
    private JFXButton loginB;

    @FXML
    private JFXButton signUpB;

    @FXML
    private JFXPasswordField passwordTF;

    @FXML
    private Label errorL;

    private DataAccess dataAccess;

    public LoginScreenController() throws SQLException {
        dataAccess = new DataAccess();
    }


    private void setupLoginB() {

        loginB.setOnAction(e -> {

            if (userNameTF.getText().equals("") || passwordTF.getText().equals("")) {
                errorL.setText("Please enter a valid user name and password.");
                return;
            }

            try {

                lockLoginScreen();
                int userID = dataAccess.getUserID(userNameTF.getText(), passwordTF.getText());
                unlockLoginScreen();

                if (userID < 0)
                    errorL.setText("Please enter a valid user name and password.");
                else {

                    String userImagePath = dataAccess.getUserImagePath(userID);
                    Main.user = new User((short) userID, userNameTF.getText());
                    if (userImagePath != null)
                        Main.user.setUserImagePath(userImagePath);

                    TrayNotification trayNotification = new TrayNotification();
                    trayNotification.setAnimationType(AnimationType.POPUP);
                    trayNotification.setNotificationType(NotificationType.SUCCESS);
                    trayNotification.setMessage("Welcome " + userNameTF.getText());
                    trayNotification.setTitle("Logged in successfully");
                    trayNotification.showAndWait();

                    //Main.screenManager.changeScreen(null); // add here the new screen.

                }

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

        });

    }

    private void setupSignUpB() {

        signUpB.setOnAction(e -> {
            errorL.setText("");
            // Main.screenManager.changeScreen(null); // pass here the sign up screen
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
                loginB.fire();
            else if (e.getCode() == KeyCode.UP)
                userNameTF.requestFocus();

        });

    }

    private void lockLoginScreen() {
        userNameTF.setDisable(true);
        passwordTF.setDisable(true);
        loginB.setDisable(true);
        signUpB.setDisable(true);
    }

    private void unlockLoginScreen() {
        userNameTF.setDisable(false);
        passwordTF.setDisable(false);
        loginB.setDisable(false);
        signUpB.setDisable(false);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupLoginB();
        setupSignUpB();
        setupUserNameTF();
        setupPasswordTF();
    }

}
