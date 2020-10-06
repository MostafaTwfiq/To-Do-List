package GUI.Style.Style.ExtraComponents;

import javafx.scene.paint.Color;

public class SearchBoxTheme {

    private final Color searchBoxColor;
    private final Color textColor;
    private final Color promptTextColor;

    public SearchBoxTheme(Color searchBoxColor, Color textColor, Color promptTextColor) {
        this.searchBoxColor = searchBoxColor;
        this.textColor = textColor;
        this.promptTextColor = promptTextColor;
    }

    public Color getSearchBoxColor() {
        return searchBoxColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public Color getPromptTextColor() {
        return promptTextColor;
    }
}
