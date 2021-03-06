package GUI.Screens.LoginScreen;

import BCrypt.BCrypt;
import DataBase.DataAccess;
import DataClasses.User;
import GUI.IControllers;
import GUI.Screens.MainScreen.MainScreenController;
import GUI.Screens.SignUpScreen.SignUpScreenController;
import GUI.Style.ScreensPaths;
import GUI.Style.Style.Theme;
import GUI.Style.StyleFactory;
import Main.Main;
import com.github.plushaze.traynotification.animations.Animations;
import com.github.plushaze.traynotification.notification.Notifications;
import com.github.plushaze.traynotification.notification.TrayNotification;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginScreenController implements IControllers {

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

            lockLoginScreen();

            try {

                int userID = dataAccess.getUserID(userNameTF.getText());

                if (userID < 0)
                    errorL.setText("Please enter a valid user name and password.");
                else {

                    if (!BCrypt.checkpw(passwordTF.getText(), dataAccess.getUserPassword(userID)))
                        errorL.setText("Please enter a valid user name and password.");
                    else {
                        String userImagePath = dataAccess.getUserImagePath(userID);
                        Theme userTheme = dataAccess.getUserTheme(userID);
                        Main.user = new User((short) userID, userNameTF.getText(), userTheme);
                        if (userImagePath != null)
                            Main.user.setUserImagePath(userImagePath);

                        Main.theme = new StyleFactory().generateTheme(userTheme);
                        Main.screenManager.updateScreensStyle();

                        try {
                            MainScreenController mainScreenController = new MainScreenController();
                            Parent mainScreenParent = null;
                            ScreensPaths paths = new ScreensPaths();

                            FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.getMainScreenFxml()));
                            loader.setController(mainScreenController);
                            mainScreenParent = loader.load();
                            mainScreenParent.getStylesheets().add(paths.getMainScreenCssSheet());

                            Main.screenManager.changeScreen(mainScreenController);

                            TrayNotification trayNotification = new TrayNotification();
                            trayNotification.setAnimation(Animations.POPUP);
                            trayNotification.setNotification(Notifications.SUCCESS);
                            trayNotification.setMessage("Welcome " + userNameTF.getText());
                            trayNotification.setTitle("Logged in successfully");
                            trayNotification.showAndDismiss(Duration.seconds(1));

                            errorL.setText("");
                            userNameTF.clear();
                            passwordTF.clear();

                        } catch (Exception exception) {
                            errorL.setText("Something went wrong please try again.");
                            exception.printStackTrace();
                        }

                    }

                }

            } catch (SQLException sqlException) {
                errorL.setText("Something went wrong please try again.");
                sqlException.printStackTrace();
            }

            unlockLoginScreen();

        });

    }

    private void setupRegisterLblBtn() {

        registerLblBtn.setOnMouseClicked(e -> {
            errorL.setText("");

            try {
                SignUpScreenController signUpScreenController = new SignUpScreenController();
                Parent signUpScreenParent = null;
                ScreensPaths paths = new ScreensPaths();

                FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.getSignUpScreenFxml()));
                loader.setController(signUpScreenController);
                signUpScreenParent = loader.load();
                signUpScreenParent.getStylesheets().add(paths.getSignUpScreenCssSheet());

                Main.screenManager.changeScreen(signUpScreenController);

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

    @Override
    public void updateStyle() {
        loginPane.getStylesheets().clear();
        loginPane.getStylesheets().add(new ScreensPaths().getLoginScreenCssSheet());
    }

    @Override
    public Parent getParent() {
        return loginPane;
    }

}
