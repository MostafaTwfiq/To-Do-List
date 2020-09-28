package GUI.Screens.AddTaskScreen;

import DataBase.DataAccess;
import DataClasses.Task;
import DataClasses.TaskStatus.TaskPriority;
import GUI.IControllers;
import GUI.Style.ScreensPaths;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddTaskScreen implements IControllers {

    private Task task;

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
    private JFXComboBox<String> HourDropDwn;

    @FXML
    private JFXComboBox<String> MinuteDropDwn;

    @FXML
    private JFXButton DeleteBtn;

    @FXML
    private JFXButton CancelBtn;

    @FXML
    private JFXButton AddBtn;

    private JFXComboBox<String> PriorityComboBox;

    private DataAccess dataAccess;

    private final ObservableList<String> taskPriorityItems  = FXCollections.observableArrayList();
    private final ObservableList<String> HrComboBoxItems  = FXCollections.observableArrayList();
    private final ObservableList<String> MinComboBoxItems  = FXCollections.observableArrayList();



    public AddTaskScreen() {

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
        setupMainTitle();

        setupDeleteBtn();
        setupAddBtn();
        setupCancelBtn();

        setupHrComboBox();
        setupMinComboBox();
        setUpPriorityComboBox();
    }


    private void setupAddBtn(){
        if(this.task!=null){
            this.AddBtn.setText("Save");

            this.AddBtn.setOnAction(e->{
                try {
                    dataAccess.updateTask(this.task.getTaskID(),
                            this.TaskTitle.getText(),"",
                            this.task.getTaskStatus(),
                            this.task.getPriority());
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            });

        }else{
            this.AddBtn.setText("Add");

            this.AddBtn.setOnAction(e->{

            });
        }
    }

    private void setupCancelBtn(){
        CancelBtn.setOnAction(e->{

        });
    }

    private void setupDeleteBtn(){
        if(this.task!=null)

            DeleteBtn.setOnAction(e->{
                try {

                    for (String note :
                            this.task.getNotes()) {

                        this.dataAccess.deleteNote(this.task.getTaskID(),note);
                    }

                    this.dataAccess.deleteTask(this.task.getTaskID());

                }catch(SQLException sqlException){

                    sqlException.printStackTrace();
                }
            });
        else
            this.DeleteBtn.setVisible(false);
    }

    private void setupMainTitle(){
        if(this.task!=null)
            this.MainTitle.setText("Edit Task");
        else
            this.MainTitle.setText("Add New Task");
    }

    private void setupHrComboBox(){
        this.HrComboBoxItems.addAll(Stream.iterate(0, n -> n + 1)
                                          .limit(24)
                                          .collect(Collectors.toList())
                                          .stream()
                                          .map(Object::toString)
                                          .collect(Collectors.toList()));
    }

    private void setupMinComboBox(){
        this.MinComboBoxItems.addAll(Stream.iterate(0, n -> n + 1)
                                           .limit(60)
                                           .collect(Collectors.toList())
                                           .stream()
                                           .map(Object::toString)
                                           .collect(Collectors.toList()));
    }


    private void setUpPriorityComboBox(){
        this.taskPriorityItems.addAll(Arrays.asList(Arrays
                .stream(TaskPriority.class.getEnumConstants())
                .map(Enum::toString).toArray(String[]::new)));
    }
}
