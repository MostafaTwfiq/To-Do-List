package GUI.Screens.MainScreen.TasksOverview;

import DataClasses.Task;
import GUI.Screens.MainScreen.IControllersObserver;
import GUI.Style.ScreensPaths;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

public class TasksOverviewList extends VBox implements IControllersObserver {

    private List<TaskOverviewController> taskOverviewControllers;

    private TaskOverviewAddTaskController taskOverviewAddTaskController;

    private List<IControllersObserver> observers;

    public TasksOverviewList() {

        taskOverviewControllers = new Vector<>();
        observers = new ArrayList<>();

        setupLayout();

    }

    public TasksOverviewList(List<IControllersObserver> observers) {

        taskOverviewControllers = new Vector<>();
        this.observers = observers;

        setupLayout();

    }

    private void setupLayout() {

        styleProperty().set("-fx-background-color: transparent; " +
                "-fx-border-color: transparent;");

        setPadding(new Insets(5, 5, 5, 5));
        setSpacing(5);

    }

    public void displayTasks(List<Task> tasks) {

        setOpacity(0);
        Timeline displayingTasksAnimation = new Timeline(
                new KeyFrame(
                        Duration.millis(300),
                        new KeyValue(opacityProperty(), 1)
                )
        );

        displayingTasksAnimation.setCycleCount(1);
        displayingTasksAnimation.play();
        getChildren().clear();
        taskOverviewControllers.clear();

        ScreensPaths paths = new ScreensPaths();
        Parent parentLayout = null;

        for (Task task : tasks) {

            try {

                TaskOverviewController taskOverviewController = new TaskOverviewController(task, this);

                FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.getTaskOverviewFxml()));
                loader.setController(taskOverviewController);
                parentLayout = loader.load();
                parentLayout.getStylesheets().add(paths.getTaskOverviewCssSheet());

                taskOverviewControllers.add(taskOverviewController);
                getChildren().add(parentLayout);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        try {

            taskOverviewAddTaskController = new TaskOverviewAddTaskController(this);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.getTaskOverviewAddTaskFxml()));
            loader.setController(taskOverviewAddTaskController);
            parentLayout = loader.load();
            parentLayout.getStylesheets().add(paths.getTaskOverviewAddTaskSheet());

            getChildren().add(parentLayout);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateStyle() {

        for (TaskOverviewController controller : taskOverviewControllers)
            controller.updateStyle();

        taskOverviewAddTaskController.updateStyle();

    }

    @Override
    public void updateTasks() {

        for (IControllersObserver observer : observers)
            observer.updateTasks();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void addObserver(IControllersObserver observer) {

        if (observer == null)
            throw new IllegalArgumentException();

        observers.add(observer);

    }

    public List<Task> getCurrentTasks() {
        Vector<Task> tasks = new Vector<>();
        for (TaskOverviewController controller : taskOverviewControllers)
            tasks.add(controller.getTask());

        return tasks;

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
