package GUI.Screens.userProfileScreen;
import GUI.IControllers;
import GUI.Style.ScreensPaths;
import GUI.Style.Style.Theme;
import GUI.Style.StyleFactory;
import Main.Main;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.net.URL;
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
    private Label errorL;

    @FXML
    private JFXPasswordField confirmPasswordTF;

    @FXML
    private JFXComboBox<String> themesComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUserImage();
        setupThemesComboBox();

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

    @Override
    public Parent getParent() {
        return this.parentPane;
    }
}
