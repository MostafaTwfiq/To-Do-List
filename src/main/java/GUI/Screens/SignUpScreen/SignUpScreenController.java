package GUI.Screens.SignUpScreen;

import BCrypt.BCrypt;
import DataBase.DataAccess;
import GUI.IControllers;
import GUI.Style.ScreensPaths;
import GUI.Style.StyleFactory;
import GUI.Style.Style.Theme;
import Main.Main;
import com.github.plushaze.traynotification.animations.Animations;
import com.github.plushaze.traynotification.notification.Notifications;
import com.github.plushaze.traynotification.notification.TrayNotification;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SignUpScreenController implements IControllers {

    @FXML
    private AnchorPane signUpPane;

    @FXML
    private Circle userImageC;

    @FXML
    private Button chooseImageB;

    @FXML
    private JFXTextField userNameTF;

    @FXML
    private JFXButton signUpB;

    @FXML
    private JFXPasswordField passwordTF;

    @FXML
    private Label errorL;

    @FXML
    private JFXPasswordField confirmPasswordTF;

    @FXML
    private JFXComboBox<String> themesComboBox;

    @FXML
    private Button returnB;

    private String userImagePath;

    private DataAccess dataAccess;

    private final String imageExtensionsRegex = "^\\.(jpe?g|png|gif|bmp)$";

    public SignUpScreenController() throws SQLException {
        dataAccess = new DataAccess();
        userImagePath = null;
    }

    private void setupUserImageC() {
        loadUserImageCImage();
    }

    private void loadUserImageCImage() {

        try {

            Image image = new Image(Main.theme.getThemeResourcesPath() + "SignUpScreen/defaultUserProfileImage.png");
            setUserImageCImage(image);

        } catch (Exception e) {
            System.out.println("can't load default user profile image in sign up screen.");
        }

    }

    private void setUserImageCImage(Image image) {
        ImagePattern pattern = new ImagePattern(image);
        userImageC.setFill(pattern);
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

        loadChooseImageBImage();

        chooseImageB.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(new Stage());

            updateUserImageFile(file);

        });

    }

    private void loadChooseImageBImage() {
        chooseImageB.setGraphic(loadButtonImage(Main.theme.getThemeResourcesPath()
                + "SignUpScreen/chooseImage.png", 35, 40));
    }

    private void updateUserImageFile(File file) {
        if (file != null) {

            String filePath = file.getPath();
            String fileExtension = filePath.substring(filePath.lastIndexOf('.'));
            if (!Pattern.matches(imageExtensionsRegex, fileExtension))
                return;

            userImagePath = filePath;

            try {
                Image image = new Image(new FileInputStream(userImagePath));
                setUserImageCImage(image);
            } catch (Exception exception) {
                userImagePath = null;
            }

        }
    }

    private void setupSignUpB() {

        signUpB.setOnAction(e -> {

            if (userNameTF.getText().trim().equals("") || passwordTF.getText().equals("")) {
                errorL.setText("Please enter a valid user name and password.");
                return;
            } else if (!passwordTF.getText().equals(confirmPasswordTF.getText())) {
                errorL.setText("Passwords don't match.");
                return;
            }

            lockSignUpScreen();

            try {

                if (dataAccess.checkIfUserNameExists(userNameTF.getText())){
                    errorL.setText("This user name already exits.");
                }  else {

                    Theme userTheme = Theme.valueOf(themesComboBox.getValue());
                    dataAccess.addNewUser(userNameTF.getText(), BCrypt.hashpw(passwordTF.getText(), BCrypt.gensalt()), userTheme);

                    if (userImagePath != null)
                        dataAccess.addNewUserImage(dataAccess.getLastInsertedID(), userImagePath);

                    TrayNotification notification = new TrayNotification();
                    notification.setTitle("New user has been added successfully");
                    notification.setMessage("Welcome " + userNameTF.getText());
                    notification.setAnimation(Animations.POPUP);
                    notification.setNotification(Notifications.SUCCESS);
                    notification.showAndDismiss(Duration.seconds(1));

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

        loadCancelBImage();

        returnB.setOnAction(e -> {
            Main.screenManager.changeToLastScreen();
        });

    }

    private void loadCancelBImage() {
        returnB.setGraphic(loadButtonImage(Main.theme.getThemeResourcesPath()
                + "SignUpScreen/return.png", 50, 50));
    }

    private ImageView loadButtonImage(String path, double h, double w) {

        try {

            Image buttonImage = new Image(path);
            ImageView buttonImageView = new ImageView(buttonImage);
            buttonImageView.setFitHeight(h);
            buttonImageView.setFitWidth(w);

            return buttonImageView;

        } catch (Exception e) {
            System.out.println("There is an error happened while loading images.");
        }

        return new ImageView();

    }


    private void setupUserNameTF() {

        userNameTF.setOnKeyReleased(e -> {

            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.DOWN)
                passwordTF.requestFocus();

        });

    }

    private void setupPasswordTF() {

        passwordTF.setOnKeyReleased(e -> {

            if (e.getCode() == KeyCode.UP)
                userNameTF.requestFocus();
            else if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.DOWN)
                confirmPasswordTF.requestFocus();

        });

    }

    private void setupConfirmPasswordTF() {

        confirmPasswordTF.setOnKeyReleased(e -> {

            if (e.getCode() == KeyCode.ENTER)
                signUpB.fire();
            else if (e.getCode() == KeyCode.UP)
                passwordTF.requestFocus();

        });

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

    private void lockSignUpScreen() {
        userNameTF.setDisable(true);
        passwordTF.setDisable(true);
        confirmPasswordTF.setDisable(true);
        signUpB.setDisable(true);
        returnB.setDisable(true);
    }

    private void unlockSignUpScreen() {
        userNameTF.setDisable(false);
        passwordTF.setDisable(false);
        confirmPasswordTF.setDisable(false);
        signUpB.setDisable(false);
        returnB.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupSignUpPane();
        setupUserImageC();
        setupSignUpB();
        setupChooseImageB();
        setupCancelB();
        setupUserNameTF();
        setupPasswordTF();
        setupConfirmPasswordTF();
        setupThemesComboBox();
    }

    @Override
    public void updateStyle() {
        signUpPane.getStylesheets().clear();
        signUpPane.getStylesheets().add(new ScreensPaths().getSignUpScreenCssSheet());

        if (userImagePath == null)
            loadUserImageCImage();

        loadCancelBImage();
        loadChooseImageBImage();
    }

    @Override
    public Parent getParent() {
        return signUpPane;
    }

}
