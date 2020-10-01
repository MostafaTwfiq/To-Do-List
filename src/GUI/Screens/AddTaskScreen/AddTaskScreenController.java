package GUI.Screens.AddTaskScreen;

import DataBase.DataAccess;
import DataClasses.DateFormat;
import DataClasses.TaskStatus.TaskPriority;
import DataClasses.TaskStatus.TaskStatus;
import GUI.IControllers;
import GUI.Style.ScreensPaths;
import GUI.Style.StyleFactory;
import Main.Main;
import com.jfoenix.controls.*;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import tray.animations.AnimationType;
import tray.animations.FadeAnimation;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class AddTaskScreenController implements IControllers {

    @FXML
    private AnchorPane parentPane;

    @FXML
    private JFXDatePicker DateDropDwn;

    @FXML
    private JFXChipView<String> TagsChipView;

    @FXML
    private GridPane NotesGridView;

    @FXML
    private JFXTextField TaskTitle;

    @FXML
    private Label errLabel;

    @FXML
    private Label MainTitle;

    @FXML
    private JFXButton DeleteBtn;

    @FXML
    private JFXButton CancelBtn;

    @FXML
    private JFXComboBox<String> PriorityComboBox;

    @FXML
    private JFXButton AddBtn;

    @FXML
    private JFXButton StarBtn;

    @FXML
    private JFXTimePicker timePicker;

    private DataAccess dataAccess;

    private Boolean isStarred = false;

    private StringConverter<LocalTime> defaultConverter = new LocalTimeStringConverter(FormatStyle.SHORT, Locale.UK);

    private Runnable updateFuncRef;


    public AddTaskScreenController() {}

    public void setUpdateFunction(Runnable func){
        this.updateFuncRef = func;
    }

    @Override
    public void updateStyle() {
        parentPane.getStylesheets().clear();
        parentPane.getStylesheets().add(new ScreensPaths().getEditTaskScreenCssSheet());
    }


    @Override
    public Parent getParent() {
        return this.parentPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupCancelBtn();
        setupStarButton();
        setupAddBtn();

        setUpPriorityComboBox();
        setUpTagsChipView();
        timePicker.set24HourView(true);
        timePicker.setConverter(defaultConverter);

        try {
             this.dataAccess = new DataAccess();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private void setupAddBtn(){
        this.AddBtn.setOnAction(e->{
            handle(e);
        });
    }

    private void setupCancelBtn(){
        CancelBtn.setOnAction(e->{
            cancel();
        });
    }

    private void setupStarButton(){

        StarBtn.setGraphic(prepareStarredImage(32));

        this.StarBtn.setOnMouseClicked(e->{
            isStarred  = !isStarred;
            StarBtn.setGraphic(prepareStarredImage(32));
        });

    }

    private ImageView prepareStarredImage(int dim){
        ImageView imgV = null;
        try {
            if (this.isStarred) {
                imgV = new ImageView(
                        new Image(
                                new FileInputStream(Main.theme.getThemeResourcesPath() + "MainScreen/starred.png")
                        )
                );
            } else {
                imgV = new ImageView(new Image(
                        new FileInputStream(Main.theme.getThemeResourcesPath() + "MainScreen/notStarred.png")
                ));
            }
            imgV.setFitHeight(dim);
            imgV.setFitWidth(dim);
        } catch (Exception e) {
            System.out.println("Something went wrong while loading starred button image.");
        }

        return imgV;

    }

    private void setUpTagsChipView(){
        this.TagsChipView.setMaxWidth(300);
        this.TagsChipView.setPrefWidth(300);
        this.TagsChipView.setMaxWidth(300);

    }
            
    private void setUpPriorityComboBox(){
        this.PriorityComboBox.getItems().addAll(Arrays
                .stream(TaskPriority.class.getEnumConstants())
                .map(Enum::toString).toArray(String[]::new));
    }

    private void cancel(){
        Main.theme = new StyleFactory().generateTheme(Main.user.getTheme());
        Main.screenManager.updateScreensStyle();
        Main.screenManager.changeToLastScreen();
    }


   // TODO : migrate this to utils
    private <T> T last(T[] array) {
        return array[array.length - 1];
    }

    @SuppressWarnings("unchecked")
    private <T extends Enum<T>> T newEnumInstance(String name, Type type) {
        return Enum.valueOf((Class<T>) type, name);
    }

    private String checkFlds(){
        StringJoiner errorsbldr  = new StringJoiner("\n");

        if(DateDropDwn.getValue()==null){
            errorsbldr.add("Task Date missing");
        }

        if(timePicker.getValue()==null){
            errorsbldr.add("Task Completion Date is missing.");
        }

        if(TaskTitle.getText().isBlank()){
            errorsbldr.add("Task Title is missing");
        }
        return errorsbldr.toString();
    }

    private void handle(ActionEvent e) {
        try {
            var res  =  checkFlds();

            if(res.isBlank() || res.isEmpty()) {
                String datetime = DateDropDwn.getValue()
                        .format(DateTimeFormatter
                                .ofPattern(DateFormat.getDateFormat())) +
                        " "
                        +
                        timePicker.getValue()
                                .format(DateTimeFormatter
                                        .ofPattern(last(DateFormat.getDateTimeFormat().split(" "))));

                dataAccess.addNewTask(
                        Main.user.getUserID(),
                        this.TaskTitle.getText(),
                        datetime,
                        TaskStatus.NOT_DONE,
                        TaskPriority.valueOf(PriorityComboBox.getValue()),
                        this.isStarred);

                int insertedTaskID = this.dataAccess.getLastInsertedID();

                for (String tag :
                        this.TagsChipView.getChips()) {
                    dataAccess.addNewTag(insertedTaskID, tag);
                }

                TrayNotification trayNotification = new TrayNotification();
                trayNotification.setAnimationType(AnimationType.SLIDE);
                trayNotification.setNotificationType(NotificationType.SUCCESS);
                trayNotification.setMessage("Successfully created a reminder!");
                trayNotification.setTitle("Success");
                trayNotification.showAndDismiss(Duration.seconds(30));

                this.updateFuncRef.run();

                cancel();
            }else{
                this.errLabel.setText(res);
                FadeTransition  fadetrans = new FadeTransition(Duration.millis(1000));
                fadetrans.setNode(this.errLabel);

                fadetrans.setFromValue(0.0);
                fadetrans.setToValue(1.0);
                fadetrans.setCycleCount(2);

                fadetrans.setAutoReverse(true);
                fadetrans.playFromStart();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
