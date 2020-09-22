package GUI.Screens.MainScreen;

import DataBase.DataAccess;
import GUI.IControllers;
import GUI.MultiProgressBar.MultiProgressBar;
import GUI.ProgressBar.ProgressBar;
import GUI.SearchBox.SearchBox;
import GUI.Style.Style.ExtraComponents.ProgressBarTheme;
import GUI.Style.Style.ExtraComponents.SearchBoxTheme;
import GUI.Style.Style.Theme;
import GUI.Style.StyleFactory;
import Main.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
    private JFXListView<Node> optionsListView;

    @FXML
    private AnchorPane filtersLayout;

    @FXML
    private Label filtersLbl;

    @FXML
    private JFXChipView<String> filtersChipView;

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

    private OptionsLabels optionsLabels;

    private SearchBox searchBox;

    private MultiProgressBar multiProgressBar;

    private ProgressBar progressBar;


    public MainScreenController() throws Exception {
        dataAccess = new DataAccess();
    }


    private void setupSearchBox() {

        SearchBoxTheme searchBoxTheme = Main.theme.getSearchBoxTheme();

        searchBox = new SearchBox(
                20, 290,
                "Search",
                e -> searchForTasks(),
                searchBoxTheme.getSearchBoxColor(),
                searchBoxTheme.getTextColor(),
                searchBoxTheme.getPromptTextColor()

        );

        searchBoxLayout.getChildren().add(searchBox);

        AnchorPane.setTopAnchor(searchBox, 0.0);
        AnchorPane.setBottomAnchor(searchBox, 0.0);
        AnchorPane.setRightAnchor(searchBox, 5.0);
        AnchorPane.setLeftAnchor(searchBox, 5.0);

    }

    private void searchForTasks() {

    }

    private void setupUserOverview() {
        setupUserImage();
        setupUserName();
        setupUserSettingsB();
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

    private void setupUserSettingsB() {

        setUserSettingBImage();

        userSettingsB.setOnAction(e -> {

        });

    }

    private void setUserSettingBImage() {

        try {

            FileInputStream imageStream = new FileInputStream(
                    Main.theme.getThemeResourcesPath() + "MainScreen/settings.png"
            );

            Image buttonImage = new Image(imageStream);
            ImageView buttonImageView = new ImageView(buttonImage);
            buttonImageView.setFitHeight(20);
            buttonImageView.setFitWidth(20);

            userSettingsB.setGraphic(buttonImageView);

        } catch (Exception e) {
            System.out.println("There is an error happened while loading images.");
        }

    }

    private void setupDatePicker() {

        datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {

            if (oldValue == null || !oldValue.isEqual(newValue)) {
                searchForTasks();
            }

        });

    }

    private void setupOptionsListView() {

        optionsLabels = new OptionsLabels();
        optionsLabels.getTodayLbl().setOnMouseClicked(e -> {

        });

        optionsLabels.getSortByReminderLbl().setOnMouseClicked(e -> {

        });

        optionsLabels.getSortByPriorityLbl().setOnMouseClicked(e -> {

        });

        optionsListView.getItems().add(optionsLabels.getTodayLbl());
        optionsListView.getItems().add(optionsLabels.getSortByReminderLbl());
        optionsListView.getItems().add(optionsLabels.getSortByPriorityLbl());

    }

    @Override
    public void updateStyle() {

    }

    @Override
    public Parent getParent() {
        return parentLayout;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUserOverview();
        setupSearchBox();
        setupDatePicker();
        setupOptionsListView();
    }


}
