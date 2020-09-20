package GUI.Screens.LoginScreen;

import DataBase.DataAccess;
import DataClasses.User;
import GUI.Screens.SignUpScreen.SignUpScreenController;
import Main.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
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
    private JFXPasswordField passwordTF;

    @FXML
    private Label errorL;

    @FXML
    private HBox registerLblHBx;

    @FXML
    private Label registerLblTxt;

    @FXML
    private Label registerLblBtn;

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

    private void setupRegisterLblBtn() {

        registerLblBtn.setOnMouseClicked(e -> {
            errorL.setText("");

            try {
                SignUpScreenController signUpScreenController = new SignUpScreenController();
                Parent signUpScreenParent = null;

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Screens/SignUpScreen/SignUpScreenDesign.fxml"));
                loader.setController(signUpScreenController);
                signUpScreenParent = loader.load();
                signUpScreenParent.getStylesheets().add("GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/SignUpScreen/SignUpSheet.css");

                Parent finalSignUpScreenParent = signUpScreenParent;
                Main.screenManager.changeScreen(signUpScreenParent, event -> {
                    finalSignUpScreenParent.getStylesheets().clear();
                    finalSignUpScreenParent.getStylesheets().add("GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/SignUpScreen/SignUpSheet.css");
                });

            } catch (Exception exception) {
                exception.printStackTrace();
            }

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
        registerLblBtn.setDisable(true);
    }

    private void unlockLoginScreen() {
        userNameTF.setDisable(false);
        passwordTF.setDisable(false);
        loginB.setDisable(false);
        registerLblBtn.setDisable(false);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupLoginB();
        setupRegisterLblBtn();
        setupUserNameTF();
        setupPasswordTF();
    }

}
