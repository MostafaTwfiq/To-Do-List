package GUI.Style;

public abstract class Style {

    private final String themeResourcesPath;

    private final StageTheme stageStyle;

    public Style(String themeResourcesPath, StageTheme stageStyle) {
        this.themeResourcesPath = themeResourcesPath;
        this.stageStyle = stageStyle;
    }

    public String getThemeResourcesPath() {
        return themeResourcesPath;
    }

    public StageTheme getStageStyle() {
        return stageStyle;
    }

    public abstract String getThemeName();

}
