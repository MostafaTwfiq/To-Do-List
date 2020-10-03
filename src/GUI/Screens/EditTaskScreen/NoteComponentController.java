package GUI.Screens.EditTaskScreen;

import GUI.IControllers;
import GUI.Style.ScreensPaths;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class NoteComponentController implements IControllers {

    @FXML
    private HBox parentLayout;

    @FXML
    private JFXTextField noteTxtFld;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private HBox lblsLayout;

    @FXML
    private Label charCounterLbl;

    @FXML
    private Label maxLengthLbl;

    private final NoteComponentList noteComponentList;

    private final int maxLength = 50;


    private boolean isUpdated = false;

    private String initialNote = null;

    public NoteComponentController(NoteComponentList noteComponentList, String initialString) {
        this.noteComponentList = noteComponentList;
        this.initialNote = initialString;
    }

    private void setupNoteTxtField() {
        this.noteTxtFld.setText(this.initialNote);

        noteTxtFld.textProperty().addListener((ob,ol,n) -> {
            if(!this.isUpdated &&
                    !this.initialNote.equals(noteTxtFld.getText()))
                this.isUpdated = true;

            noteTxtFld.setDisable(true);

            if (noteTxtFld.getText().length() > maxLength) {
                noteTxtFld.setText(noteTxtFld.getText().substring(0, maxLength));
                noteTxtFld.positionCaret(maxLength);
            }

            int charCount  = noteTxtFld.getText().length();

            charCounterLbl.setText(String.valueOf(charCount));

            noteTxtFld.setDisable(false);

        });

        noteTxtFld.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {

                if (oldPropertyValue && !isValidNote())
                    deleteBtn.fire();

            }
        });

    }

    private void setUpDeleteBtn() {

        deleteBtn.setOnAction(e -> this.noteComponentList.notifyOfDeletion(this));

        deleteBtn.setContentDisplay(ContentDisplay.CENTER);

    }

    private void setupMaxLengthLbl() {
        maxLengthLbl.setText(String.format(" / %d", maxLength));
    }

    private void setupCharCounterLbl() {

        charCounterLbl.setText(String.valueOf(noteTxtFld.getText()
                                                        .chars()
                                                        .count()));
    }

    public String getNote() {
        return noteTxtFld.getText();
    }



    public void requestFocus() {
        noteTxtFld.requestFocus();
    }

    public boolean isValidNote() {
        return !noteTxtFld.getText().isEmpty() &&
               !noteTxtFld.getText().equals("");
    }

    public boolean isNewNote(){
        return initialNote == null   ||
               initialNote.isBlank() ||
               initialNote.isEmpty();
    }

    @Override
    public void updateStyle() {

        parentLayout.getStylesheets().clear();
        parentLayout.getStylesheets().add(new ScreensPaths().getAddTaskScreenCssSheet());

    }

    @Override
    public Parent getParent() {
        return this.parentLayout;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupNoteTxtField();
        setUpDeleteBtn();
        setupMaxLengthLbl();
        setupCharCounterLbl();
    }

    public boolean isUpdated(){
        return isUpdated;
    }

    public String getInitialNote(){
        return this.initialNote;
    }

}
