package GUI.Style;

import Main.Main;

public class ScreensPaths {

    private final String stageCssSheet = "GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/Stage/StageSheet.css";
    private final String loginScreenCssSheet = "GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/LoginScreen/LoginSheet.css";
    private final String signUpScreenCssSheet = "GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/SignUpScreen/SignUpSheet.css";
    private final String mainScreenCssSheet = "GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/MainScreen/AppMainSheet.css";
    private final String taskOverviewCssSheet = "GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/MainScreen/TaskOverviewSheet.css";
    private final String taskOverviewAddTaskSheet = "GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/MainScreen/TaskOverviewAddTaskSheet.css";


    public String getStageFxml() {
        String stageFxml = "/GUI/ScreenManager/Stage/StageDesign.fxml";
        return stageFxml;
    }

    public String getStageCssSheet() {
        return stageCssSheet;
    }

    public String getLoginScreenFxml() {
        return "/GUI/Screens/LoginScreen/LoginScreenStyle.fxml";
    }

    public String getLoginScreenCssSheet() {
        return loginScreenCssSheet;
    }

    public String getSignUpScreenFxml() {
        return "/GUI/Screens/SignUpScreen/SignUpScreenDesign.fxml";
    }

    public String getSignUpScreenCssSheet() {
        return signUpScreenCssSheet;
    }

    public String getMainScreenFxml() {
        return "/GUI/Screens/MainScreen/MainScreenDesign.fxml";
    }

    public String getMainScreenCssSheet() {
        return mainScreenCssSheet;
    }

    public String getTaskOverviewFxml() {
        return "/GUI/Screens/MainScreen/TasksOverview/TaskOverviewDesign.fxml";
    }

    public String getTaskOverviewCssSheet() {
        return taskOverviewCssSheet;
    }

    public String getTaskOverviewAddTaskFxml() {
        return "/GUI/Screens/MainScreen/TasksOverview/TaskOverviewAddTaskDesign.fxml";
    }

    public String getTaskOverviewAddTaskSheet() {
        return taskOverviewAddTaskSheet;
    }

}
