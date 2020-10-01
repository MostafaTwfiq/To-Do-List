package GUI.Style;

import Main.Main;

public class ScreensPaths {

    private final String stageCssSheet = "GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/Stage/StageSheet.css";
    private final String loginScreenCssSheet = "GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/LoginScreen/LoginSheet.css";
    private final String signUpScreenCssSheet = "GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/SignUpScreen/SignUpSheet.css";
    private final String mainScreenCssSheet = "GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/MainScreen/AppMainSheet.css";
    private final String taskOverviewCssSheet = "GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/MainScreen/TaskOverviewSheet.css";
    private final String taskOverviewAddTaskSheet = "GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/MainScreen/TaskOverviewAddTaskSheet.css";
    private final String userProfileScreenCssSheet = "GUI/Style/ThemesCss/" + Main.theme.getThemeName()+ "/UserProfileScreen/UserProfileScreen.css";
    private final String editTaskScreenCssSheet = "GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/EditTaskScreen/EditTaskScreen.css";
    private final String addTaskScreenCssSheet  = "GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/AddTaskScreen/AddTaskScreen.css";
    private final String popUpCss  = "GUI/Style/ThemesCss/"+ Main.theme.getThemeName() +"/PopUpWdw/PopUpWdw.css";

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



    public String getUserProfileScreenFxml(){return"/GUI/Screens/userProfileScreen/UserProfileDesign.fxml"; }

    public String getUserProfileScreenCssSheet(){return userProfileScreenCssSheet; }



    public String getEditTaskScreenCssSheet() {
        return editTaskScreenCssSheet;
    }

    public String getEditTaskScreenFxml(){
        return "/GUI/Screens/EditTaskScreen/EditTaskScreenDesign.fxml";
    }


    public String getAddTaskScreenFxml() {
        return "/GUI/Screens/AddTaskScreen/AddTaskScreenDesign.fxml";
    }

    public String getAddTaskScreenCssSheet() {
        return addTaskScreenCssSheet;
    }

}
