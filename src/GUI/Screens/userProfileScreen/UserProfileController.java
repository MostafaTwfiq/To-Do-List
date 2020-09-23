package GUI.Screens.userProfileScreen;
import DataBase.DataAccess;
import GUI.IControllers;
import GUI.Screens.LoginScreen.LoginScreenController;
import GUI.Style.ScreensPaths;
import GUI.Style.Style.Theme;
import GUI.Style.StyleFactory;
import Main.Main;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.security.PrivilegedAction;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserProfileController implements IControllers  {
    @FXML
    private AnchorPane parentPane;

    @FXML
    private ImageView userImageV;

    @FXML
    private Button chooseImageB;

    @FXML
    private JFXPasswordField passwordTF;

    @FXML
    private JFXTextField userNameTF;

    @FXML
    private Label errorL;

    @FXML
    private JFXPasswordField confirmPasswordTF;

    @FXML
    private JFXComboBox<String> themesComboBox;

    @FXML
    private Button DeleteAccBtn;

    @FXML
    private Button CancelBtn;

    @FXML
    private Button SaveBtn;

    private DataAccess dataAccess;


    public UserProfileController() throws Exception{
        this.dataAccess = new DataAccess();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUserImage();
        setupThemesComboBox();
        // setting up buttons
        setDeleteAccBtn();
        setSaveSettings();
        setCancelButton();
        setupChooseImageB();
    }

    private void setupUserImage() {
        String imagePath = Main.user.getUserImagePath();

        try {

            FileInputStream fileInputStream = new FileInputStream(imagePath);
            Image image = new Image(fileInputStream);
            userImageV.setImage(image);

        } catch (Exception e) {

            try {
                imagePath = Main.user.getDefaultUserImagePath();
                FileInputStream fileInputStream = new FileInputStream(imagePath);
                Image image = new Image(fileInputStream);
                userImageV.setImage(image);
            } catch (Exception e1) {
                System.out.println("There is something wrong happened while trying to load user image.");
            }

        }
    }

    private void setupThemesComboBox() {

        Theme[] themes = Theme.values();
        for (Theme theme : themes)
            themesComboBox.getItems().add(theme.toString());

        themesComboBox.setValue(Main.theme.getThemeName());
        themesComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null && !newValue.equals(oldValue)) {
                    Main.theme = new StyleFactory().generateTheme(Theme.valueOf(newValue));
                    Main.screenManager.updateScreensStyle();
                }
            }
        });
    }

    @Override
    public void updateStyle() {
        parentPane.getStylesheets().clear();
        parentPane.getStylesheets().add(new ScreensPaths().getUserProfileScreenCssSheet());
    }


    private void setDeleteAccBtn(){
        DeleteAccBtn.setOnAction(e->{
            DeleteAccount();
        });
    }

    private void setSaveSettings(){
        SaveBtn.setOnAction(e->{
            try {
                SaveSettings();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void setCancelButton(){
        CancelBtn.setOnAction(e->{
            Cancel();
        });
    }

    private void setupChooseImageB() {
        loadChooseImageBImage();
        chooseImageB.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(new Stage());
            userImageV.setImage(loadButtonImage(file.getAbsolutePath(),50,50).getImage());
        });
    }


    private void loadChooseImageBImage() {
        chooseImageB.setGraphic(loadButtonImage(Main.theme.getThemeResourcesPath()
                + "/SignUpScreen/chooseImage.png", 35, 40));
    }

    private ImageView loadButtonImage(String path, double h, double w) {

        try {

            FileInputStream imageStream = new FileInputStream(path);

            Image buttonImage = new Image(imageStream);
            ImageView buttonImageView = new ImageView(buttonImage);
            buttonImageView.setFitHeight(h);
            buttonImageView.setFitWidth(w);

            return buttonImageView;

        } catch (Exception e) {
            System.out.println("There is an error happened while loading images.");
        }

        return new ImageView();

    }

    public void SaveSettings() throws SQLException {
        if(!userNameTF.getText().equals(Main.user.getUserName()) &&
           !userNameTF.getText().contains(" "))
        {
            dataAccess.updateUserName(Main.user.getUserID(), userNameTF.getText());
            Main.user.setUserName(userNameTF.getText());
        }
        if(confirmPasswordTF.getText().equals(passwordTF.getText())) {
            dataAccess.updateUserPassword(Main.user.getUserID(), passwordTF.getText());
        }
        dataAccess.updateUserImage(Main.user.getUserID(),userImageV.getImage().getUrl());
        Main.user.setUserImagePath(userImageV.getImage().getUrl());
    }

    public void Cancel(){
        try {
            Main.screenManager.changeToLastScreen();
        } catch (Exception exception) {
            exception.printStackTrace();
        };
    }

    private void DeleteAccount(){
        // Deletes the user and goes to the main sign up screen
        try {
            lockUserProfileScreen();
            dataAccess.deleteUser(Main.user.getUserID());
            unlockUserProfileScreen();
        } catch (SQLException throwables) {
            System.out.println("Failed to delete the user.");
        }
        // clean up
        Main.user = null;

        TrayNotification notification = new TrayNotification();
        notification.setTitle("Successfully Deleted User.");
        notification.setMessage("Welcome " + userNameTF.getText());
        notification.setAnimationType(AnimationType.POPUP);
        notification.setNotificationType(NotificationType.SUCCESS);
        notification.showAndDismiss(Duration.seconds(1.0));

        try {
            LoginScreenController loginScreenController = new LoginScreenController();
            Parent mainScreenParent = null;
            ScreensPaths paths = new ScreensPaths();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.getLoginScreenCssSheet()));
            loader.setController(loginScreenController);
            mainScreenParent = loader.load();
            mainScreenParent.getStylesheets().add(paths.getMainScreenCssSheet());

            Main.screenManager.changeScreen(loginScreenController);

        } catch (Exception exception) {
            exception.printStackTrace();
        };
    }


    private void lockUserProfileScreen(){
        passwordTF.setDisable(true);
        confirmPasswordTF.setDisable(true);
        userNameTF.setDisable(true);
    }

    private void unlockUserProfileScreen(){
        passwordTF.setDisable(false);
        confirmPasswordTF.setDisable(false);
        userNameTF.setDisable(false);
    }

    @Override
    public Parent getParent() {
        return this.parentPane;
    }
}
