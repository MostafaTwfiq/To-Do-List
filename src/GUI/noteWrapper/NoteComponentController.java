package GUI.noteWrapper;

import GUI.IControllers;
import Main.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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

    private NoteVBox noteVBox;

    private final int maxLength = 50;

    private boolean isNew = true;


    private UnaryOperator<TextFormatter.Change> modifyChange;


    public NoteComponentController(NoteVBox noteVBox) {
        this.noteVBox = noteVBox;
    }

    private void setupNoteTxtField() {
        noteTxtFld.setOnKeyPressed(e -> {

           /* if(!updated)
                updated = true;*/

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
    }

    private void setUpDeleteBtn() {

        deleteBtn.setOnAction(e -> {
            this.noteVBox.notifyOfDeletion(this);
        });

        deleteBtn.setContentDisplay(ContentDisplay.CENTER);
        setDeleteBtnImage();

    }

    private void setDeleteBtnImage() {

        try {

            deleteBtn.setGraphic(
                    new ImageView(
                            new Image(
                                    new FileInputStream(
                                            Main.theme.getThemeResourcesPath() + "")
                            )
                    )
            );

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

    public boolean isNewNotes(){
        return this.isNew;
    }

    @Override
    public void updateStyle() {
        setDeleteBtnImage();
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
