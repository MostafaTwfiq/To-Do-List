package GUI.Screens.AddTaskScreen;

import DataBase.DataAccess;
import DataClasses.DateFormat;
import DataClasses.Task;
import DataClasses.TaskStatus.TaskPriority;
import DataClasses.TaskStatus.TaskStatus;
import GUI.IControllers;
import GUI.Screens.MainScreen.IControllersObserver;
import GUI.Screens.PopUpWindw.PopUpWdwController;
import GUI.Style.ScreensPaths;
import GUI.Style.StyleFactory;
import Main.Main;
import com.jfoenix.controls.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.FileInputStream;
import java.io.IOException;
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
    private Label MainTitle;

    @FXML
    private JFXButton DeleteBtn;

    @FXML
    private JFXButton CancelBtn;

    @FXML
    private JFXButton AddBtn;

    @FXML
    private JFXComboBox<String> PriorityComboBox;

    @FXML
    private JFXButton AddTagBtn;

    @FXML
    private JFXButton StarBtn;

    @FXML
    private JFXTimePicker timePicker;

    private DataAccess dataAccess;

    private Boolean isStarred = false;

    private StringConverter<LocalTime> defaultConverter = new LocalTimeStringConverter(FormatStyle.SHORT, Locale.UK);

    private Runnable updateFuncRef;


    public AddTaskScreenController() {}

    public void setObservers(Runnable func){
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

        setupAddBtn();
        setupCancelBtn();
        setupStarButton();
        setupAddTagsbtn();

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


    private void setupAddTagsbtn(){
        this.AddTagBtn.setOnMouseClicked(e->{
            var res = showTagPopUpWindow();
            if(res!=null){
                this.TagsChipView.getChips().add(res);
            }
        });
    }


    private String showTagPopUpWindow(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(new ScreensPaths().getPopUpFxml()));
        // initializing the controller
        PopUpWdwController popupController = new PopUpWdwController();
        popupController.setInvoker(this);
        loader.setController(popupController);
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            Stage popupStage = new Stage(StageStyle.UNDECORATED);

            popupController.setStage(popupStage);
            if(this.getParent().getScene().getWindow().getScene()!=null) {
                popupStage.initOwner(this.getParent().getScene().getWindow());
            }
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.setScene(scene);
            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return popupController.getResult();
    }

    private void setupAddBtn(){
            this.AddBtn.setText("Save");

            this.AddBtn.setOnAction(this::handle);
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


    private void handle(ActionEvent e) {
        try {

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

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
