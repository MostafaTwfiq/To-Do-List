package GUI.Screens.PopUpWindw;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PopUpWdwController extends AbstractController implements Initializable{

    @FXML
    private JFXTextField tagFld;

    @FXML
    private JFXButton okBtn;

    @FXML
    private  JFXButton CancelBtn;

    private String result;

    private Stage stage = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setOkButton();
        setupCancelBtn();
    }

    private void setOkButton(){
        okBtn.setOnMouseClicked(e->{
            result = tagFld.getText();
            closeStage();
        });
    }

    private void setupCancelBtn(){
        this.CancelBtn.setOnMouseClicked(e->{
            result = "";
            closeStage();
        });
    }

    public String getResult(){
        return this.result;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void closeStage() {
        if(stage!=null) {
            stage.close();
        }
    }
}
