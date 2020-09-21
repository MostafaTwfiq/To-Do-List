package GUI.Screens.MainScreen;

import DataBase.DataAccess;
import GUI.IControllers;
import Main.Main;
import javafx.scene.Parent;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainScreenController implements IControllers {


    @FXML
    private AnchorPane parentLayout;

    @FXML
    private AnchorPane toolsLayout;

    @FXML
    private AnchorPane searchBoxLayout;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private ScrollPane optionsScrollPane;

    @FXML
    private AnchorPane optionsListLayout;

    @FXML
    private JFXListView<Label> optionsListView;

    @FXML
    private AnchorPane filtersLayout;

    @FXML
    private JFXChipView<String> filtersChipView;

    @FXML
    private Label filtersLbl;

    @FXML
    private VBox progressBarsLayout;

    @FXML
    private HBox userOverviewLayout;

    @FXML
    private ImageView userImage;

    @FXML
    private Label userNameLbl;

    @FXML
    private Button userSettingsB;

    @FXML
    private AnchorPane tasksViewLayout;

    @FXML
    private ScrollPane tasksScrollPane;

    private DataAccess dataAccess;

    public MainScreenController() throws Exception {
        dataAccess = new DataAccess();
    }

    private void setupUserOverview() {
        setupUserImage();
        setupUserName();
    }

    private void setupUserImage() {
        String imagePath = Main.user.getUserImagePath();

        try {

            FileInputStream fileInputStream = new FileInputStream(imagePath);
            Image image = new Image(fileInputStream);
            userImage.setImage(image);

        } catch (Exception e) {

            try {
                imagePath = Main.user.getDefaultUserImagePath();
                FileInputStream fileInputStream = new FileInputStream(imagePath);
                Image image = new Image(fileInputStream);
                userImage.setImage(image);
            } catch (Exception e1) {
                System.out.println("There is something wrong happened while trying to load user image.");
            }

        }
    }


    private void setupUserName() {
        userNameLbl.setText(Main.user.getUserName());
    }

    private void setup

    @Override
    public void updateStyle() {

    }

    @Override
    public Parent getParent() {
        return parentLayout;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
