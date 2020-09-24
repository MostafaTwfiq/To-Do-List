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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
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
import java.util.regex.Pattern;

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

    @FXML
    private JFXPasswordField oldPasswordTF;

    private String imagePath;

    private DataAccess dataAccess;

    private final String imageExtensionsRegex = "^\\.(jpe?g|png|gif|bmp)$";


    public UserProfileController() throws Exception{
        this.dataAccess = new DataAccess();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupParentPane();
        setupUserImage();
        setupThemesComboBox();
        setUserNameLabel();
        // setting up buttons
        setDeleteAccBtn();
        setSaveSettings();
        setCancelButton();
        setupChooseImageB();
    }


    private void setupParentPane() {

        parentPane.setOnDragEntered(e -> parentPane.setOpacity(0.5));
        parentPane.setOnDragExited(e -> parentPane.setOpacity(1));
        parentPane.setOnDragOver(new EventHandler<>() {
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

    private void setupUserImage() {
        String imagePath = Main.user.getUserImagePath();

        try {
            userImageV.setImage(new Image(new FileInputStream(imagePath)));
        } catch (Exception e) {

            try {
                imagePath = Main.user.getDefaultUserImagePath();
                userImageV.setImage(new Image(new FileInputStream(imagePath)));
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
                    Theme newTheme = Theme.valueOf(newValue);
                    Main.theme = new StyleFactory().generateTheme(newTheme);
                    Main.screenManager.updateScreensStyle();
                }
            }
        });
    }

    @Override
    public void updateStyle() {
        loadChooseImageBImage();
        parentPane.getStylesheets().clear();
        parentPane.getStylesheets().add(new ScreensPaths().getUserProfileScreenCssSheet());
    }




    private void setUserNameLabel(){
        userNameTF.setText(Main.user.getUserName());
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

            if(file != null)
                updateUserImageFile(file);

        });
    }

    private void updateUserImageFile(File file) {
        if (file != null) {

            String filePath = file.getPath();
            String fileExtension = filePath.substring(filePath.lastIndexOf('.'));
            if (!Pattern.matches(imageExtensionsRegex, fileExtension))
                return;

            imagePath = filePath;

            try {
                FileInputStream fileInputStream = new FileInputStream(imagePath);
                Image image = new Image(fileInputStream);
                userImageV.setImage(image);
            } catch (Exception exception) {
                imagePath = null;
            }

        }
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


    private boolean checkIfValidUserName(String userName) {
        if (userName.contains(" "))
            return false;

        return true;
    }

    private void SaveSettings() throws SQLException {

        int userID = Main.user.getUserID();

        if (dataAccess.getUserID(Main.user.getUserName(), oldPasswordTF.getText()) != userID) {
            errorL.setText("The old password is incorrect.");
            return;
        }

        if (!userNameTF.getText().equals(Main.user.getUserName())) {

            if (!checkIfValidUserName(userNameTF.getText())) {
                errorL.setText("Please enter new valid user name.");
                return;
            }
            else if (!dataAccess.checkIfUserNameExists(userNameTF.getText())) {
                dataAccess.updateUserName(userID, userNameTF.getText());
                Main.user.setUserName(userNameTF.getText());
            }
            else {
                errorL.setText("UserName is already taken.");
                return;
            }

        }

        if (!isBlankOrEmpty(passwordTF.getText())) {

            if (passwordTF.getText().equals(confirmPasswordTF.getText()))
                dataAccess.updateUserPassword(userID, passwordTF.getText());
            else {
                errorL.setText("Passwords don't match.");
                return;
            }

        }


        if(imagePath != null){

           if(Main.user.getUserImagePath().equals(Main.user.getDefaultUserImagePath()))
               dataAccess.addNewUserImage(Main.user.getUserID(), imagePath);
           else
               dataAccess.updateUserImage(Main.user.getUserID(), imagePath);

           Main.user.setUserImagePath(imagePath);

        }

        if(!themesComboBox.valueProperty().get().equals(Main.user.getTheme().toString())) {
            dataAccess.updateUserTheme(Main.user.getUserID(), Theme.valueOf(themesComboBox.valueProperty().get()));
            Main.user.setTheme(Theme.valueOf(themesComboBox.valueProperty().get()));
        }

        Cancel();

    }

    private void Cancel(){
            Main.theme = new StyleFactory().generateTheme(Main.user.getTheme());
            Main.screenManager.updateScreensStyle();
            Main.screenManager.changeToLastScreen();
    }

    private void DeleteAccount(){

        lockUserProfileScreen();
        // Deletes the user and goes to the main sign up screen
        try {
            dataAccess.deleteUser(Main.user.getUserID());
        } catch (SQLException throwables) {
            System.out.println("Failed to delete the user.");
        }

        unlockUserProfileScreen();

        // clean up
        Main.user = null;

        TrayNotification notification = new TrayNotification();
        notification.setTitle("Successfully Deleted User.");
        notification.setMessage("Good bye " + userNameTF.getText());
        notification.setAnimationType(AnimationType.POPUP);
        notification.setNotificationType(NotificationType.SUCCESS);
        notification.showAndDismiss(Duration.seconds(1.0));

        Main.screenManager.returnNumOfScreens(2);

    }


    private void lockUserProfileScreen(){
        passwordTF.setDisable(true);
        confirmPasswordTF.setDisable(true);
        userNameTF.setDisable(true);
        oldPasswordTF.setDisable(true);
        SaveBtn.setDisable(true);
        CancelBtn.setDisable(true);
        DeleteAccBtn.setDisable(true);
        themesComboBox.setDisable(true);
    }

    private void unlockUserProfileScreen(){
        passwordTF.setDisable(false);
        confirmPasswordTF.setDisable(false);
        userNameTF.setDisable(false);
        oldPasswordTF.setDisable(false);
        SaveBtn.setDisable(false);
        CancelBtn.setDisable(false);
        DeleteAccBtn.setDisable(false);
        themesComboBox.setDisable(false);
    }

    @Override
    public Parent getParent() {
        return this.parentPane;
    }
    private Boolean isBlankOrEmpty(String str){
        return str.isBlank() || str.isEmpty();
    }
}
