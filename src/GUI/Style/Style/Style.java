package GUI.Style.Style;

import GUI.Style.Style.ExtraComponents.MultiProgressBarTheme;
import GUI.Style.Style.ExtraComponents.ProgressBarTheme;
import GUI.Style.Style.ExtraComponents.SearchBoxTheme;
import GUI.Style.Style.ExtraComponents.StageTheme;
import javafx.scene.paint.Color;

public abstract class Style {

    private final String themeResourcesPath;

    private final StageTheme stageStyle;

    private final SearchBoxTheme searchBoxTheme;

    private final MultiProgressBarTheme multiProgressBarTheme;

    private final ProgressBarTheme progressBarTheme;

    private final Color optionsLabelsC;

    public Style(String themeResourcesPath,
                 StageTheme stageStyle,
                 SearchBoxTheme searchBoxTheme,
                 MultiProgressBarTheme multiProgressBarTheme,
                 ProgressBarTheme progressBarTheme,
                 Color optionsLabelsC) {
        this.themeResourcesPath = themeResourcesPath;
        this.stageStyle = stageStyle;
        this.searchBoxTheme = searchBoxTheme;
        this.multiProgressBarTheme = multiProgressBarTheme;
        this.progressBarTheme = progressBarTheme;
        this.optionsLabelsC = optionsLabelsC;
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

    public abstract String getThemeName();

}
