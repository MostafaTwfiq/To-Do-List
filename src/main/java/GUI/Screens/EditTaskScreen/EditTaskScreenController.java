package GUI.Screens.EditTaskScreen;

import DataBase.DataAccess;
import DataClasses.DateFormat;
import DataClasses.Task;
import DataClasses.TaskStatus.TaskPriority;
import DataClasses.TaskStatus.TaskStatus;
import GUI.IControllers;
import GUI.Style.ScreensPaths;
import Main.Main;
import com.jfoenix.controls.*;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class EditTaskScreenController implements IControllers {

    @FXML
    private AnchorPane parentPane;

    @FXML
    private Label titleLbl;

    @FXML
    private JFXDatePicker dateDropDwn;

    @FXML
    private JFXChipView<String> tagsChipView;

    @FXML
    private JFXTextField taskTitleTxtFld;

    @FXML
    private Label tagsLbl;

    @FXML
    private Label onLbl;

    @FXML
    private Label notesLbl;

    @FXML
    private Label mainTitle;

    @FXML
    private Label atLbl;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXComboBox<String> priorityComboBox;

    @FXML
    private Label priorityLbl;

    @FXML
    private JFXButton starBtn;

    @FXML
    private JFXTimePicker timePicker;

    @FXML
    private Label errorLbl;

    @FXML
    private JFXButton addNoteBtn;

    @FXML
    private ScrollPane notesScrollPane;

    @FXML
    private Label titleCounterLbl;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private JFXComboBox<String> taskStatusCB;

    @FXML
    private Label statusLbl;

    private final NoteComponentList noteComponentList;

    private DataAccess dataAccess;

    private Boolean isStarred = false;

    private StringConverter<LocalTime> defaultConverter = new LocalTimeStringConverter(FormatStyle.SHORT, Locale.UK);

    private Runnable updateFuncRef;

    private final int titleMaxLength = 50;

    private final Task taskBeingEdited;


    public EditTaskScreenController(Task toEdit) {
        noteComponentList = new NoteComponentList();
        try {
            this.dataAccess = new DataAccess();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        taskBeingEdited = toEdit;
        prepareTask();
    }

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

        notesScrollPane.setContent(noteComponentList);
        setUpPriorityComboBox();
        setupTaskStatusComboBox();
        timePicker.set24HourView(true);
        timePicker.setConverter(defaultConverter);

        setupTaskTitleTxtField();
        setUpNoteComponentList();
        setupCancelBtn();
        setupStarButton();
        setupAddBtn();
        setAddNoteBtn();
        setUpTimePicker();
        setUpDatePicker();
        setupTagsChipContainer();
        setupDeleteButton();

    }

    private void setupTaskTitleTxtField() {

        taskTitleTxtFld.setText(taskBeingEdited.getTitle());

        titleCounterLbl.setText(
                taskTitleTxtFld.getText().length() + " / " + titleMaxLength
        );


        taskTitleTxtFld.textProperty().addListener( (ob, ol, n) -> {

            taskTitleTxtFld.setDisable(true);

            if (taskTitleTxtFld.getText().length() > titleMaxLength)
                taskTitleTxtFld.setText(taskTitleTxtFld.getText().substring(0, titleMaxLength));

            titleCounterLbl.setText(
                    taskTitleTxtFld.getText().length() + " / " + titleMaxLength
            );

            taskTitleTxtFld.setDisable(false);

            taskTitleTxtFld.positionCaret(titleMaxLength);

        });

    }

    private void setupAddBtn(){
        this.updateBtn.setOnAction(this::handle);
    }

    private void setAddNoteBtn(){
        //TODO get the stuff from the database on load instead
        this.addNoteBtn.setOnAction(e->{
            this.noteComponentList.addNote("");
        });
    }


    private void setupDeleteButton(){
        deleteBtn.setOnAction(e-> {

            if (taskBeingEdited.getDateTime().toLocalDate().isBefore(LocalDate.now())) {
                popupErrorText("You can't Delete a task older than today.");
                return;
            }

            try {
                dataAccess.deleteTask(taskBeingEdited.getTaskID());
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

            updateFuncRef.run();
            cancel();

        });
    }

    private void setupTagsChipContainer(){
        for (String tag:
             taskBeingEdited.getTags()) {
            tagsChipView.getChips().add(tag);
        }
    }

    private void setUpNoteComponentList(){
        List<String> notes = this.taskBeingEdited.getNotes();
        for (String note : notes) {
            this.noteComponentList.addNote(note);
        }
    }

    private void setupCancelBtn(){
        cancelBtn.setOnAction(e->{
            cancel();
        });
    }

    private void setupStarButton(){
        isStarred = this.taskBeingEdited.isStarred();

        starBtn.setContentDisplay(ContentDisplay.CENTER);
        starBtn.setGraphic(prepareStarredImage(30));

        this.starBtn.setOnMouseClicked(e->{
            isStarred  = !isStarred;
            starBtn.setGraphic(prepareStarredImage(30));
        });

    }

    private ImageView prepareStarredImage(int dim){
        ImageView imgV = null;
        try {
            if (this.isStarred) {
                imgV = new ImageView(
                        new Image(
                                Main.theme.getThemeResourcesPath() + "AddTaskScreen/starred.png"
                        )
                );
            } else {
                imgV = new ImageView(new Image(
                       Main.theme.getThemeResourcesPath() + "AddTaskScreen/notStarred.png"
                ));
            }
            imgV.setFitHeight(dim);
            imgV.setFitWidth(dim);
        } catch (Exception e) {
            System.out.println("Something went wrong while loading starred button image.");
        }

        return imgV;

    }
            
    private void setUpPriorityComboBox() {

        priorityComboBox.getItems().addAll(Arrays
                .stream(TaskPriority.class.getEnumConstants())
                .map(Enum::toString).toArray(String[]::new));

        priorityComboBox.setValue(taskBeingEdited.getPriority().toString());

    }

    private void setupTaskStatusComboBox() {
        taskStatusCB.getItems().addAll(Arrays
                .stream(TaskStatus.class.getEnumConstants())
                .map(Enum::toString).toArray(String[]::new));

        taskStatusCB.setValue(taskBeingEdited.getTaskStatus().toString());

    }

    private void cancel(){
        Main.screenManager.changeToLastScreen();
    }

    private void setUpDatePicker(){
        dateDropDwn.setValue(LocalDate.from(this.taskBeingEdited.getDateTime()));
    }

    private void setUpTimePicker(){
        this.timePicker.setValue(LocalTime.from(this.taskBeingEdited.getDateTime()));
    }


   // TODO : migrate this to utils
    private <T> T last(T[] array) {
        return array[array.length - 1];
    }

    private String checkFields(){
        StringJoiner errorsBuilder  = new StringJoiner("\n");

        if(dateDropDwn.getValue() == null){
            errorsBuilder.add("Task date missing.");
        }

        if(timePicker.getValue() == null){
            errorsBuilder.add("Task reminding time is missing.");
        }

        if(taskTitleTxtFld.getText().isBlank()){
            errorsBuilder.add("Task title is missing.");
        }

        if (priorityComboBox.getValue() == null) {
            errorsBuilder.add("Task priority is missing.");
        }

        return errorsBuilder.toString();

    }

    private void handle(ActionEvent e) {
        try {

            String res  =  checkFields();
            if (!res.isBlank() && !res.isEmpty()) {
                popupErrorText(res);
                return;
            }

            String dateTime = dateDropDwn.getValue()
                    .format(DateTimeFormatter
                            .ofPattern(DateFormat.getDateFormat())) +
                    " "
                    +
                    timePicker.getValue()
                            .format(DateTimeFormatter
                                    .ofPattern(last(DateFormat.getDateTimeFormat().split(" "))));

            if (taskBeingEdited.getDateTime().toLocalDate().isBefore(LocalDate.now())) {
                popupErrorText("You can't update a task older than today.");
                return;
            } else if (dateDropDwn.getValue().isBefore(LocalDate.now())) {
                popupErrorText("You can't update the task date to a date older than today.");
                return;
            }

            dataAccess.updateTask(
                    taskBeingEdited.getTaskID(),
                    taskTitleTxtFld.getText(),
                    dateTime,
                    TaskStatus.valueOf(taskStatusCB.getValue()),
                    TaskPriority.valueOf(priorityComboBox.getValue()),
                    isStarred
            );


            tagsChipView.getChips()
                        .stream()
                        .filter(tag -> !taskBeingEdited.getTags().contains(tag))
                        .forEach(this::addNewTagsToDB);


            taskBeingEdited.getTags()
                           .stream()
                           .filter(tag -> !tagsChipView.getChips().contains(tag))
                           .forEach(this::deleteTagsFromDB);


            for (NoteComponentController noteComponent: noteComponentList.getNotesComponents())
                executeNoteStatus(noteComponent);

            popupSuccessNotification();

            updateFuncRef.run();

            cancel();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    private void executeNoteStatus(NoteComponentController noteComponent) {

        try {

            switch (noteComponent.getNoteStatus()) {
                case ADD_NOTE:
                    dataAccess.addNewNote(taskBeingEdited.getTaskID(), noteComponent.getNote());
                case UPDATE_NOTE:
                    dataAccess.updateTaskNote(taskBeingEdited.getTaskID(), noteComponent.getInitialNote(), noteComponent.getNote());
                case DELETE_NOTE:
                    dataAccess.deleteNote(taskBeingEdited.getTaskID(), noteComponent.getInitialNote());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void popupSuccessNotification() {
        TrayNotification trayNotification = new TrayNotification();
        trayNotification.setAnimationType(AnimationType.FADE);
        trayNotification.setNotificationType(NotificationType.SUCCESS);
        trayNotification.setMessage("Successfully updated the task.");
        trayNotification.setTitle("Success");
        trayNotification.showAndDismiss(Duration.seconds(1));
    }

    private void popupErrorText(String errorTxt) {
        errorLbl.setText(errorTxt);
        FadeTransition fadeTrans = new FadeTransition(Duration.seconds(2));
        fadeTrans.setNode(errorLbl);

        fadeTrans.setFromValue(0.0);
        fadeTrans.setToValue(1.0);
        fadeTrans.setCycleCount(2);

        fadeTrans.setAutoReverse(true);
        fadeTrans.playFromStart();
    }


    private void prepareTask(){
        try {
            taskBeingEdited.setNotes(dataAccess.getTaskNotes(taskBeingEdited.getTaskID()));
            taskBeingEdited.setTags(dataAccess.getTaskTags(taskBeingEdited.getTaskID()));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private void addNewTagsToDB(String tag) {
        try {
            dataAccess.addNewTag(taskBeingEdited.getTaskID(), tag);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private void deleteTagsFromDB(String o) {
        try {
            dataAccess.deleteTag(taskBeingEdited.getTaskID(), o);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
