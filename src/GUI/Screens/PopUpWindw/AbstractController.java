package GUI.Screens.PopUpWindw;

import javafx.fxml.Initializable;

public abstract class AbstractController {

    protected Initializable main;

    public void setInvoker(Initializable main) {
        this.main = main;
    }
}
