package GUI.Screens.AddTaskScreen;

import GUI.IControllers;
import GUI.Style.ScreensPaths;
import Main.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

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

    private NoteComponentList noteComponentList;

    private final int maxLength = 50;


    public NoteComponentController(NoteComponentList noteComponentList) {
        this.noteComponentList = noteComponentList;
    }

    private void setupNoteTxtField() {

        noteTxtFld.setOnKeyPressed(e -> {

            noteTxtFld.setDisable(true);

            if (noteTxtFld.getText().length() > maxLength)
                noteTxtFld.setText(noteTxtFld.getText().substring(0, maxLength));

            long charCount  = this.noteTxtFld.getText()
                                             .chars()
                                             .count();

            charCounterLbl.setText(String.valueOf(charCount));

            noteTxtFld.setDisable(false);

            noteTxtFld.positionCaret(maxLength);

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
        setDeleteBtnImage();

    }

    private void setDeleteBtnImage() {

        try {

            ImageView buttonImage =  new ImageView(
                    new Image(
                            new FileInputStream(
                                    Main.theme.getThemeResourcesPath() + "AddTaskScreen/deleteB.png")
                    )
            );

            buttonImage.setFitWidth(15);
            buttonImage.setFitHeight(15);

            deleteBtn.setGraphic(buttonImage);

        } catch (Exception e) {
            System.out.println("Something went wrong while loading delete note button.");
        }

    }

    private void setupMaxLengthLbl() {
        maxLengthLbl.setText(String.format(" / %d", maxLength));
    }

    private void setupCharCounterLbl() {
        charCounterLbl.setText("0");
    }

    public String getNote() {
        return noteTxtFld.getText();
    }

    public void requestFocus() {
        noteTxtFld.requestFocus();
    }

    public boolean isValidNote() {
        return !noteTxtFld.getText().isEmpty() && !noteTxtFld.getText().equals("");
    }

    @Override
    public void updateStyle() {

        setDeleteBtnImage();

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

}
