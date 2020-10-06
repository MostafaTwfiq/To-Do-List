package GUI.Style.Style;

import GUI.Style.Style.ExtraComponents.*;
import javafx.scene.paint.Color;

public abstract class Style {

    private final String themeResourcesPath;

    private final StageTheme stageStyle;

    private final SearchBoxTheme searchBoxTheme;

    private final MultiProgressBarTheme multiProgressBarTheme;

    private final ProgressBarTheme progressBarTheme;

    private final Color optionsLabelsC;

    private final PrioritiesColors prioritiesColors;

    private final PopUpOptionsTheme popUpOptionsTheme;

    public Style(String themeResourcesPath,
                 StageTheme stageStyle,
                 SearchBoxTheme searchBoxTheme,
                 MultiProgressBarTheme multiProgressBarTheme,
                 ProgressBarTheme progressBarTheme,
                 Color optionsLabelsC,
                 PrioritiesColors prioritiesColors,
                 PopUpOptionsTheme popUpOptionsTheme) {

        this.themeResourcesPath = themeResourcesPath;
        this.stageStyle = stageStyle;
        this.searchBoxTheme = searchBoxTheme;
        this.multiProgressBarTheme = multiProgressBarTheme;
        this.progressBarTheme = progressBarTheme;
        this.optionsLabelsC = optionsLabelsC;
        this.prioritiesColors = prioritiesColors;
        this.popUpOptionsTheme = popUpOptionsTheme;

    }

    public String getThemeResourcesPath() {
        return themeResourcesPath;
    }

    public StageTheme getStageStyle() {
        return stageStyle;
    }

    public SearchBoxTheme getSearchBoxTheme() {
        return searchBoxTheme;
    }

    public MultiProgressBarTheme getMultiProgressBarTheme() {
        return multiProgressBarTheme;
    }

    public ProgressBarTheme getProgressBarTheme() {
        return progressBarTheme;
    }

    public Color getOptionsLabelsC() {
        return optionsLabelsC;
    }

    public PrioritiesColors getPrioritiesColors() {
        return prioritiesColors;
    }

    public PopUpOptionsTheme getPopUpOptionsTheme() {
        return popUpOptionsTheme;
    }

    public abstract String getThemeName();
}
