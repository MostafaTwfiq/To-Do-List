package GUI.Screens.SignUpScreen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpScreenController implements Initializable {

    @FXML
    private AnchorPane signUpPane;

    @FXML
    private ImageView userImageV;

    @FXML
    private Button chooseImageB;

    @FXML
    private JFXTextField userNameTF;

    @FXML
    private JFXButton cancelB;

    @FXML
    private JFXButton signUpB;

    @FXML
    private JFXPasswordField passwordTF;

    @FXML
    private Label errorL;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    
}
