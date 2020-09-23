package GUI.Screens.MainScreen.TasksOverview;

import DataBase.DataAccess;
import DataClasses.Task;
import DataClasses.TaskStatus.TaskPriority;
import DataClasses.TaskStatus.TaskStatus;
import GUI.Screens.MainScreen.IControllersObserver;
import GUI.Style.ColorTransformer;
import GUI.Style.ScreensPaths;
import GUI.Style.Style.ExtraComponents.PopUpOptionsTheme;
import GUI.Style.Style.ExtraComponents.PrioritiesColorGetter;
import Main.Main;
import com.jfoenix.controls.JFXPopup;
import javafx.scene.Cursor;
import javafx.scene.Parent;

import java.io.FileInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class TaskOverviewController implements IControllersObserver {

    @FXML
    private HBox parentLayout;

    @FXML
    private JFXButton tskDoneStatusBtn;

    @FXML
    private Label taskNameLbl;

    @FXML
    private Label taskTimeLbl;

    @FXML
    private Rectangle taskPriorityRec;

    @FXML
    private JFXButton starredBtn;

    @FXML
    private Button moreOptionsBtn;

    private JFXPopup popupOptions;

    private DataAccess dataAccess;

    private Task task;

    private IControllersObserver observer;

    public TaskOverviewController(Task task, IControllersObserver observer) throws SQLException {
        this.task = task;
        this.observer = observer;
        dataAccess = new DataAccess();
    }

    public TaskOverviewController(Task task) throws SQLException {
        this.task = task;
        this.observer = null;

        dataAccess = new DataAccess();
    }


    private void setupTskDoneStatusBtn() {

        tskDoneStatusBtn.setOnAction(e -> {

            task.setTaskStatus(task.getTaskStatus() == TaskStatus.NOT_DONE ? TaskStatus.DONE : TaskStatus.NOT_DONE);

            try {
                dataAccess.updateTaskStatus(task.getTaskID(), task.getTaskStatus());
                setTskDoneStatusBtnStatus();
                updateTasks();
            } catch (Exception exception) {
                System.out.println("Something went wrong while trying to update task status in the database.");
            }

        });

        tskDoneStatusBtn.setContentDisplay(ContentDisplay.CENTER);
        setTskDoneStatusBtnStatus();

    }

    private void setTskDoneStatusBtnStatus() {

        if (task.getTaskStatus() == TaskStatus.DONE) {

            tskDoneStatusBtn.setGraphic(loadButtonImage(
                    Main.theme.getThemeResourcesPath() + "MainScreen/taskDone.png"
                    , 29, 29));

        } else {
            tskDoneStatusBtn.setGraphic(null);
        }

    }

    private void setupMoreOptionsBtn() {

        moreOptionsBtn.setOnMouseClicked(e -> {

            popupOptions.show(moreOptionsBtn,
                    JFXPopup.PopupVPosition.BOTTOM, JFXPopup.PopupHPosition.LEFT);

        });

        setMoreOptionsBtnImage();

    }

    private void setupPopupOptions() {
        popupOptions = new JFXPopup();
        ColorTransformer colorTransformer = new ColorTransformer();
        PopUpOptionsTheme popUpOptionsTheme = Main.theme.getPopUpOptionsTheme();

        JFXButton viewBtn = new JFXButton("View");
        JFXButton removeBtn = new JFXButton("Remove");
        VBox vBox = new VBox(viewBtn, removeBtn);
        vBox.styleProperty().set("-fx-background-color: " + colorTransformer.colorToHex(popUpOptionsTheme.getListBackgroundC()) + ";");
        popupOptions.setPopupContent(vBox);

        viewBtn.setCursor(Cursor.HAND);
        viewBtn.setStyle("-fx-background-color: transparent;"
                + "-fx-background-radius: 0;"
                + "-fx-border-color: transparent;"
                + "-fx-border-radius: 0;"
                + "-fx-text-fill:" + colorTransformer.colorToHex(popUpOptionsTheme.getButtonsTextC()) + ";"
        );
        viewBtn.setPrefWidth(90);
        viewBtn.setOnAction(e -> {
            //open here the view task screen
        });


        removeBtn.setCursor(Cursor.HAND);
        removeBtn.setStyle("-fx-background-color: transparent;"
                + "-fx-background-radius: 0;"
                + "-fx-border-color: transparent;"
                + "-fx-border-radius: 0;"
                + "-fx-text-fill:" + colorTransformer.colorToHex(popUpOptionsTheme.getButtonsTextC()) + ";"
        );
        removeBtn.setPrefWidth(90);
        removeBtn.setOnAction(e -> {
            try {
                dataAccess.deleteTask(task.getTaskID());
                popupOptions.hide();
                updateTasks();
            } catch (SQLException sqlException) {
                System.out.println("Something went wrong while trying to delete a task from the database.");
            }
        });

    }

    private void setMoreOptionsBtnImage() {

        moreOptionsBtn.setGraphic(loadButtonImage(
                Main.theme.getThemeResourcesPath() + "MainScreen/moreOptions.png",
                30, 30)
        );

    }

    private void setupStarredBtn() {

        starredBtn.setOnMouseClicked(e -> {
            task.setStarred(!task.isStarred());

            try {
                dataAccess.updateTaskStarredStatus(task.getTaskID(), task.isStarred());
                setStarredBtnImage();
                updateTasks();
            } catch (Exception exception) {
                System.out.println("Something went wrong while trying to update task is starred status in the database.");
            }
        });

        starredBtn.setContentDisplay(ContentDisplay.CENTER);
        setStarredBtnImage();
    }

    private void setStarredBtnImage() {

        if (task.isStarred()) {

            starredBtn.setGraphic(loadButtonImage(
                    Main.theme.getThemeResourcesPath() + "MainScreen/starred.png"
                    , 30, 30));

        } else {
            starredBtn.setGraphic(loadButtonImage(
                    Main.theme.getThemeResourcesPath() + "MainScreen/notStarred.png"
                    , 30, 30));
        }

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
            e.printStackTrace();
        }

        return new ImageView();

    }

    private void setTaskNameLbl() {
        taskNameLbl.setText(task.getTitle());
    }

    private void setTaskTimeLbl() {
        taskTimeLbl.setText(task.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    private void setTaskPriorityRec() {

        setTaskPriorityRecColor();

        taskPriorityRec.setOnMouseClicked(e -> {
            TaskPriority[] priorities = TaskPriority.values();
            int index;
            for (index = 0; index < priorities.length; index++) {
                if (priorities[index] == task.getPriority())
                    break;

            }

            index++;
            index = index >= priorities.length ? index % priorities.length : index;
            TaskPriority nextPriority = priorities[index];
            try {
                dataAccess.updateTaskPriority(task.getTaskID(), nextPriority);
                task.setPriority(nextPriority);
                setTaskPriorityRecColor();
                updateTasks();
            } catch (Exception exception) {
                System.out.println("Something wrong happened while trying to update the task priority in the database.");
            }

        });

    }

    private void setTaskPriorityRecColor() {
        taskPriorityRec.setFill(new PrioritiesColorGetter().getPriorityColor(Main.theme, task.getPriority()));
    }



    @Override
    public void updateStyle() {
        setTskDoneStatusBtnStatus();
        setMoreOptionsBtnImage();
        setStarredBtnImage();
        setTaskPriorityRecColor();
        setupPopupOptions();
        parentLayout.getStylesheets().clear();
        parentLayout.getStylesheets().add(new ScreensPaths().getMainScreenCssSheet());
    }

    @Override
    public Parent getParent() {
        return parentLayout;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTskDoneStatusBtn();
        setupMoreOptionsBtn();
        setupStarredBtn();
        setTaskNameLbl();
        setTaskTimeLbl();
        setTaskPriorityRec();
        setupPopupOptions();
    }

    @Override
    public void updateTasks() {

        if (observer != null)
            observer.updateTasks();

    }

    public IControllersObserver getObserver() {
        return observer;
    }

    public void setObserver(IControllersObserver observer) {

        if (observer == null)
            throw new IllegalArgumentException();

        this.observer = observer;

    }

    public Task getTask() {
        return task;
    }

}