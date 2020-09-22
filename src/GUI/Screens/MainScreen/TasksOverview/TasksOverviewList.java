package GUI.Screens.MainScreen.TasksOverview;

import DataClasses.Task;
import GUI.IControllers;
import GUI.Screens.MainScreen.IControllersObserver;
import GUI.Style.ScreensPaths;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

public class TasksOverviewList extends VBox implements IControllersObserver {

    private List<TaskOverviewController> taskOverviewControllers;

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

        getChildren().clear();
        taskOverviewControllers.clear();

        for (Task task : tasks) {

            try {

                TaskOverviewController taskOverviewController = new TaskOverviewController(task, observers);
                Parent parentLayout = null;

                ScreensPaths paths = new ScreensPaths();
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

    }

    @Override
    public void updateStyle() {

        for (TaskOverviewController controller : taskOverviewControllers)
            controller.updateStyle();

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
