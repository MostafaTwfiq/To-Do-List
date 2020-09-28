package GUI.Screens.EditTaskScreen;

import DataBase.DataAccess;
import DataClasses.Task;
import DataClasses.TaskStatus.TaskPriority;
import GUI.IControllers;
import GUI.Style.ScreensPaths;
import GUI.Style.StyleFactory;
import Main.Main;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EditTaskScreenController implements IControllers {

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
    private JFXButton StarBtn;

    @FXML
    private JFXButton AddBtn;

    @FXML
    private JFXButton AddTagBtn;

    private JFXComboBox<String> PriorityComboBox;

    private DataAccess dataAccess;

    private Boolean isStarred;

    private final ObservableList<String> taskPriorityItems  = FXCollections.observableArrayList();
    private final ObservableList<String> HrComboBoxItems  = FXCollections.observableArrayList();
    private final ObservableList<String> MinComboBoxItems  = FXCollections.observableArrayList();

    public EditTaskScreenController(Task task) {
        this.task = task;
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
        setupStarButton();
        setupAddTagsbtn();

        setupHrComboBox();
        setupMinComboBox();
        setUpPriorityComboBox();
        seUpChipBoxView();
    }


    private void setupAddTagsbtn(){
        this.AddTagBtn.setOnMouseClicked(e->{
            
        });
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
        }
    }

    private void setupCancelBtn(){
        CancelBtn.setOnAction(e->{
            cancel();
        });
    }

    private void seUpChipBoxView(){
        this.task.getTags()
                .forEach( e -> this.TagsChipView.getChips().add(e));
        
    }
    
    private void setupDeleteBtn(){
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
    }

    private void setupMainTitle(){
            this.MainTitle.setText("Edit Task");
    }

    private void setupHrComboBox(){
        this.HrComboBoxItems.addAll(Stream.iterate(0, n -> n + 1)
                                          .limit(24)
                                          .collect(Collectors.toList())
                                          .stream()
                                          .map(Object::toString)
                                          .collect(Collectors.toList()));
    }

    private void setupStarButton(){

        Background background = new Background(prepareStaredImage());
        this.StarBtn.setBackground(background);

        this.StarBtn.setOnMouseClicked(e->{
            this.task.setStarred(!this.task.isStarred());

            this.StarBtn.setBackground(new Background(prepareStaredImage()));
        });
    }

    private @NotNull BackgroundImage prepareStaredImage(){
        if(this.task.isStarred())
            return new BackgroundImage(
                    new Image(Main.theme.getThemeResourcesPath() + "MainScreen/Starred.png"),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
        else
            return new BackgroundImage(
                    new Image(Main.theme.getThemeResourcesPath() + "MainScreen/NotStarred.png"),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
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

    private void cancel(){
        Main.theme = new StyleFactory().generateTheme(Main.user.getTheme());
        Main.screenManager.updateScreensStyle();
        Main.screenManager.changeToLastScreen();
    }
}
