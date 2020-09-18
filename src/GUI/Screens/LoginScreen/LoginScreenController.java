package GUI.Screens.LoginScreen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {


    @FXML
    private AnchorPane loginPane;

    @FXML
    private Label welcomeL;

    @FXML
    private Label infoL;

    @FXML
    private JFXTextField userNameTF;

    @FXML
    private JFXButton loginB;

    @FXML
    private JFXButton signUpB;

    @FXML
    private JFXPasswordField passwordTF;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
