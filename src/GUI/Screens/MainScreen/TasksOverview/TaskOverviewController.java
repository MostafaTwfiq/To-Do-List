package GUI.Screens.MainScreen.TasksOverview;

import DataClasses.Task;
import GUI.IControllers;
import javafx.scene.Parent;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    public TaskOverviewController(Task task) {
        this.task = task;
    }

    @Override
    public void updateStyle() {

    }

    @Override
    public Parent getParent() {
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
