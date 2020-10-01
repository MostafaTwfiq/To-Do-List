package GUI.Screens.MainScreen.TasksOverview;

import GUI.Screens.AddTaskScreen.AddTaskScreenController;
import GUI.Screens.MainScreen.IControllersObserver;
import GUI.Screens.userProfileScreen.UserProfileController;
import GUI.Style.ScreensPaths;
import Main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class TaskOverviewAddTaskController implements IControllersObserver {


    @FXML
    private HBox parentLayout;

    @FXML
    private JFXButton addTaskBtn;

    @FXML
    private Label addTaskLbl;

    private IControllersObserver observer;


    public TaskOverviewAddTaskController() {
        observer = null;
    }

    public TaskOverviewAddTaskController(IControllersObserver observer) {
        this.observer = observer;
    }

    private void setupAddTaskBtn() {

        addTaskBtn.setOnAction(e -> {
            try {
                AddTaskScreenController addTaskScreenController = new AddTaskScreenController();
                Parent addTaskParent = null;
                ScreensPaths paths = new ScreensPaths();

                FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.getAddTaskScreenFxml()));
                loader.setController(addTaskScreenController);
                addTaskScreenController.setObservers(this::updateTasks);
                addTaskParent = loader.load();
                addTaskParent.getStylesheets().add(paths.getAddTaskScreenCssSheet());

                Main.screenManager.changeScreen(addTaskScreenController);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        addTaskBtn.setContentDisplay(ContentDisplay.CENTER);
        setAddBtnImage();

    }

    private void setAddBtnImage() {

        try {

            FileInputStream imageStream = new FileInputStream(
                    Main.theme.getThemeResourcesPath() + "MainScreen/addTask.png"
            );

            Image buttonImage = new Image(imageStream);
            ImageView buttonImageView = new ImageView(buttonImage);
            buttonImageView.setFitHeight(30);
            buttonImageView.setFitWidth(30);

            addTaskBtn.setGraphic(buttonImageView);

        } catch (Exception e) {
            System.out.println("There is an error happened while loading images.");
            e.printStackTrace();
        }

    }

    @Override
    public void updateStyle() {
        setAddBtnImage();
        parentLayout.getStylesheets().clear();
        parentLayout.getStylesheets().add(new ScreensPaths().getTaskOverviewAddTaskSheet());
    }

    @Override
    public Parent getParent() {
        return parentLayout;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupAddTaskBtn();
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

}
