package GUI.Screens.MainScreen;

import GUI.Style.ColorTransformer;
import Main.Main;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OptionsLabels {

    private Label todayLbl;
    private Label sortByReminderLbl;
    private Label sortByPriorityLbl;

    public OptionsLabels() {

        todayLbl = new Label("Today's tasks");
        todayLbl.setCursor(Cursor.HAND);
        sortByReminderLbl = new Label("Sort by reminder");
        sortByReminderLbl.setCursor(Cursor.HAND);
        sortByPriorityLbl = new Label("Sort by priority");
        sortByPriorityLbl.setCursor(Cursor.HAND);

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

            Image image = new Image(path);
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
