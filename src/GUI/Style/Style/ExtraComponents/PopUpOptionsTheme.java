package GUI.Style.Style.ExtraComponents;

import javafx.scene.paint.Color;

public class PopUpOptionsTheme {

    private final Color buttonsTextC;
    private final Color listBackgroundC;

    public PopUpOptionsTheme(Color buttonsTextC, Color listBackgroundC) {
        this.buttonsTextC = buttonsTextC;
        this.listBackgroundC = listBackgroundC;
    }

    public Color getButtonsTextC() {
        return buttonsTextC;
    }

    public Color getListBackgroundC() {
        return listBackgroundC;
    }

}
