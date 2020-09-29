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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddTaskScreenController implements IControllersObserver {

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

    private ArrayList<String> tags;

    private final ObservableList<String> taskPriorityItems  = FXCollections.observableArrayList();
    private final ObservableList<String> HrComboBoxItems  = FXCollections.observableArrayList();
    private final ObservableList<String> MinComboBoxItems  = FXCollections.observableArrayList();

    public AddTaskScreenController() {}


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
        loader.setLocation(getClass().getResource("Popup.fxml"));
        // initializing the controller
        PopUpWdwController popupController = new PopUpWdwController();
        loader.setController(popupController);
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            Stage popupStage = new Stage();
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

            this.AddBtn.setOnAction(e->{
                try {


                    String datetime  =  DateDropDwn.getValue()
                            .format(DateTimeFormatter
                                    .ofPattern(DateFormat.getDateFormat())) +
                            " "
                            +
                            timePicker.getValue()
                                    .format(DateTimeFormatter
                                            .ofPattern(last(DateFormat.getDateFormat().split(" "))));

                    dataAccess.addNewTask(
                            Main.user.getUserID(),
                            this.TaskTitle.getText(),
                            datetime,
                            TaskStatus.NOT_DONE,
                            newEnumInstance(PriorityComboBox.getValue(),TaskPriority.class));

                    int insertedTaskID = this.dataAccess.getLastInsertedID();

                    for (String tag:
                            this.tags) {
                        dataAccess.addNewTag(insertedTaskID,tag);
                    }

                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            });
    }

    private void setupCancelBtn(){
        CancelBtn.setOnAction(e->{
            cancel();
        });
    }


    private void setupStarButton(){

        StarBtn.setGraphic(prepareStaredImage());

        this.StarBtn.setOnMouseClicked(e->{
            isStarred  = !isStarred;
            StarBtn.setGraphic(prepareStaredImage());
        });

    }

    private ImageView prepareStaredImage(){

        try {
            if (this.isStarred) {
                return new ImageView(
                        new Image(
                                new FileInputStream(Main.theme.getThemeResourcesPath() + "MainScreen/starred.png")
                        )
                );
            } else {
                return new ImageView(
                        new Image(
                                new FileInputStream(Main.theme.getThemeResourcesPath() + "MainScreen/notStarred.png")
                        )
                );
            }
        } catch (Exception e) {
            System.out.println("Something went wrong while loading starred button image.");
        }

        return new ImageView();

    }

    private void setUpPriorityComboBox(){
        this.taskPriorityItems.addAll(Arrays.asList(Arrays
                .stream(TaskPriority.class.getEnumConstants())
                .map(Enum::toString).toArray(String[]::new)));
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

    @Override
    public void updateTasks() {

    }

}
