package GUI.Screens.MainScreen;

import GUI.Style.ColorTransformer;
import Main.Main;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class OptionsLabels {

    private Label todayLbl;
    private Label sortByReminderLbl;
    private Label sortByPriorityLbl;

    public OptionsLabels() {

        todayLbl = new Label("Today's tasks");
        sortByReminderLbl = new Label("Sort by reminder");
        sortByPriorityLbl = new Label("Sort by priority");

        setTodayLblStyle();
        setSortByReminderLblStyle();
        setSortByPriorityLblStyle();

    }


    private void setTodayLblStyle() {

        todayLbl.setGraphic(loadImage(Main.theme.getThemeResourcesPath() + "MainScreen/today.png"));

        todayLbl.setStyle(getLabelsStyle());

    }

    private void setSortByReminderLblStyle() {

        sortByReminderLbl.setGraphic(loadImage(Main.theme.getThemeResourcesPath() + "MainScreen/sortByReminder.png"));

        sortByReminderLbl.setStyle(getLabelsStyle());

    }

    private void setSortByPriorityLblStyle() {

        sortByPriorityLbl.setGraphic(loadImage(Main.theme.getThemeResourcesPath() + "MainScreen/sortByPriority.png"));

        sortByPriorityLbl.setStyle(getLabelsStyle());

    }


    private ImageView loadImage(String path) {
        try {

            FileInputStream fileInputStream = new FileInputStream(path);
            Image image = new Image(fileInputStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(22);
            imageView.setFitWidth(22);

            return imageView;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ImageView();

    }

    private String getLabelsStyle() {
        return "-fx-background-color: transparent;"
                + "-fx-border-color: transparent;"
                + "-fx-text-fill: " + new ColorTransformer().colorToHex(Main.theme.getOptionsLabelsC()) + ";"
                + "-fx-font-size: 16px;"
                + "-fx-font-weight: bold;";
    }

    public void updateStyle() {
        setTodayLblStyle();
        setSortByReminderLblStyle();
        setSortByPriorityLblStyle();
    }

    public Label getTodayLbl() {
        return todayLbl;
    }

    public Label getSortByReminderLbl() {
        return sortByReminderLbl;
    }

    public Label getSortByPriorityLbl() {
        return sortByPriorityLbl;
    }

}
