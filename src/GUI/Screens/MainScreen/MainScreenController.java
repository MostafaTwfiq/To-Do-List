package GUI.Screens.MainScreen;

import DataBase.DataAccess;
import DataClasses.DateFormat;
import DataClasses.Task;
import DataClasses.TaskStatus.TaskPriority;
import DataClasses.TaskStatus.TaskStatus;
import GUI.MultiProgressBar.MultiProgressBar;
import GUI.ProgressBar.ProgressBar;
import GUI.Screens.MainScreen.TasksOverview.TasksOverviewList;
import GUI.SearchBox.SearchBox;
import GUI.Style.Style.ExtraComponents.SearchBoxTheme;
import Main.Main;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.FileInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

public class MainScreenController implements IControllersObserver {

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

    private List<Task> todayTasks;

    private AnimationTimer reminderChecker;

    private Date todayDate;

    private TasksOverviewList tasksOverviewList;

    public MainScreenController() throws Exception {

        dataAccess = new DataAccess();
        todayTasks = new Vector<>();
        todayDate = new Date();

        tasksOverviewList = new TasksOverviewList();
        tasksOverviewList.addObserver(this);

        reminderChecker = new AnimationTimer() {
            @Override
            public void handle(long l) {

                if (todayDate.before(new Date())) {
                    loadTodayTasks();
                }

            }
        };
    }

    private void checkForReminders() {
        for (Task task : todayTasks) {
            //if (todayDate.compareTo(task.getDateTime().toLocalTime().tos))
        }
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

            if (oldValue == null || !oldValue.isEqual(newValue))
                searchForTasks();

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

    private void loadTodayTasks() {

        try {

            todayTasks = dataAccess.getTaskInfo(dataAccess.getTasksIDs(Main.user.getUserID(),
                    new SimpleDateFormat(DateFormat.getDateTimeFormat()).format(new Date()))
            );

        } catch (Exception e) {
            System.out.println("Something wrong happened while trying to load today's tasks from the database.");
        }

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
        loadTodayTasks();

        tasksScrollPane.contentProperty().set(tasksOverviewList);
        tasksOverviewList.displayTasks(todayTasks);

    }


    @Override
    public void updateTasks() {

        List<Task> tasks = searchForTasks();
        if (tasks != null)
            tasksOverviewList.displayTasks(tasks);

    }


    private List<Task> searchForTasks() {
        String title = searchBox.getText();
        String date = datePicker.valueProperty().get().format(DateTimeFormatter.ofPattern(DateFormat.getDateFormat()));
        ObservableList<String> list = filtersChipView.getChips();

        ArrayList<String> tags = new ArrayList<>();
        ArrayList<String> statuses = new ArrayList<>();
        ArrayList<String> priorities = new ArrayList<>();
        Boolean isStarred = null;
        boolean STARRED = false;
        boolean NOT_STARRED = false;

        TaskStatus[] taskStatuses = TaskStatus.values();
        TaskPriority[] taskPriorities = TaskPriority.values();
        boolean found;
        for (String filter : list) {
            found = false;

            if (filter.equals("STARRED")) {
                STARRED = true;
                continue;
            } else if (filter.equals("NOT_STARRED")) {
                NOT_STARRED = true;
                continue;
            }

            for (TaskStatus status : taskStatuses) {
                if (status.toString().equals(filter)) {
                    statuses.add(filter);
                    found = true;
                    break;
                }
            }

            if (found)
                continue;

            for (TaskPriority priority : taskPriorities) {

                if (priority.toString().equals(filter)) {
                    priorities.add(filter);
                    found = true;
                    break;
                }

            }

            if (!found)
                tags.add(filter);

        }

        isStarred = STARRED == NOT_STARRED ? null : STARRED;

        try {

            return dataAccess.getTaskInfo(
                    dataAccess.getTasksIDs(
                            Main.user.getUserID(),
                            title,
                            tags,
                            statuses,
                            priorities,
                            date,
                            isStarred
                    )
            );

        } catch (Exception e) {
            System.out.println("Something wrong happened while trying to filter tasks.");
        }

        return null;

    }

}
