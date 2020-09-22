package GUI.Screens.MainScreen.TasksOverview;

import DataClasses.Task;
import DataClasses.TaskStatus.TaskStatus;
import GUI.IControllers;
import GUI.Screens.MainScreen.IControllersObserver;
import GUI.Style.ScreensPaths;
import GUI.Style.Style.ExtraComponents.PrioritiesColorGetter;
import Main.Main;
import javafx.scene.Parent;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

public class TaskOverviewController implements IControllers {

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
    private Button moreOptionsBtn;

    private Task task;

    private List<IControllersObserver> observers;

    public TaskOverviewController(Task task, List<IControllersObserver> observers) {
        this.task = task;
        this.observers = observers;
    }

    public TaskOverviewController(Task task) {
        this.task = task;
        this.observers = new ArrayList<>();
    }


    private void setupTskDoneStatusBtn() {

        tskDoneStatusBtn.setOnAction(e -> {
            task.setTaskStatus(task.getTaskStatus() == TaskStatus.NOT_DONE ? TaskStatus.DONE : TaskStatus.NOT_DONE);

            setTskDoneStatusBtnStatus();

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

        moreOptionsBtn.setOnAction(e -> {

        });

        setMoreOptionsBtnImage();

    }

    private void setMoreOptionsBtnImage() {

        moreOptionsBtn.setGraphic(loadButtonImage(
                Main.theme.getThemeResourcesPath() + "MainScreen/moreOptions.png",
                30, 30)
        );

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
        String time = task.getDateTime().toString();
        taskTimeLbl.setText(time.substring(time.lastIndexOf('T') + 1));
    }

    private void setTaskPriorityRec() {
        taskPriorityRec.setFill(new PrioritiesColorGetter().getPriorityColor(Main.theme, task.getPriority()));
    }



    @Override
    public void updateStyle() {
        setTskDoneStatusBtnStatus();
        setMoreOptionsBtnImage();
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
        setTaskNameLbl();
        setTaskTimeLbl();
        setTaskPriorityRec();
    }

    public void addObserver(IControllersObserver observer) {

        if (observer == null)
            throw new IllegalArgumentException();

        observers.add(observer);

    }

    public void removeObserver(IControllersObserver observer) {

        if (observer == null)
            throw new IllegalArgumentException();

        observers.remove(observer);

    }

    public List<IControllersObserver> getObservers() {
        return observers;
    }

    public void setObservers(List<IControllersObserver> observers) {

        if (observers == null)
            throw new IllegalArgumentException();

        this.observers = observers;

    }

}
