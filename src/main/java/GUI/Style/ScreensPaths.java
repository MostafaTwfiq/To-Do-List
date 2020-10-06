package GUI.Style;

import Main.Main;

public class ScreensPaths {

    private final String stageCssSheet = "/ThemesCss/" + Main.theme.getThemeName() + "/Stage/StageSheet.css";
    private final String loginScreenCssSheet = "/ThemesCss/" + Main.theme.getThemeName() + "/LoginScreen/LoginSheet.css";
    private final String signUpScreenCssSheet = "/ThemesCss/" + Main.theme.getThemeName() + "/SignUpScreen/SignUpSheet.css";
    private final String mainScreenCssSheet = "/ThemesCss/" + Main.theme.getThemeName() + "/MainScreen/AppMainSheet.css";
    private final String taskOverviewCssSheet = "/ThemesCss/" + Main.theme.getThemeName() + "/MainScreen/TaskOverviewSheet.css";
    private final String taskOverviewAddTaskSheet = "/ThemesCss/" + Main.theme.getThemeName() + "/MainScreen/TaskOverviewAddTaskSheet.css";
    private final String userProfileScreenCssSheet = "/ThemesCss/" + Main.theme.getThemeName()+ "/UserProfileScreen/UserProfileScreen.css";
    private final String editTaskScreenCssSheet = "/ThemesCss/" + Main.theme.getThemeName() + "/EditTaskScreen/EditTaskScreen.css";
    private final String addTaskScreenCssSheet  = "/ThemesCss/" + Main.theme.getThemeName() + "/AddTaskScreen/AddTaskScreen.css";
    private final String addTaskNoteComponentCssSheet = "/ThemesCss/" + Main.theme.getThemeName() + "/AddTaskScreen/NoteComponent.css";
    private final String editTaskNoteComponentCssSheet = "/ThemesCss/" + Main.theme.getThemeName() + "/EditTaskScreen/NoteComponent.css";

    public String getStageFxml() {return "/screens/Stage/StageDesign.fxml"; }

    public String getStageCssSheet() {
        return stageCssSheet;
    }

    public String getSplashScreenFxml() {return "/screens/SplashScreen/SplashScreenDesign.fxml"; }

    public String getLoginScreenFxml() {
        return "/screens/LoginScreen/LoginScreenStyle.fxml";
    }

    public String getLoginScreenCssSheet() {
        return loginScreenCssSheet;
    }

    public String getSignUpScreenFxml() {
        return "/screens/SignUpScreen/SignUpScreenDesign.fxml";
    }

    public String getSignUpScreenCssSheet() {
        return signUpScreenCssSheet;
    }

    public String getMainScreenFxml() {
        return "/screens/MainScreen/MainScreenDesign.fxml";
    }

    public String getMainScreenCssSheet() {
        return mainScreenCssSheet;
    }

    public String getTaskOverviewFxml() {
        return "/screens/MainScreen/TasksOverview/TaskOverviewDesign.fxml";
    }

    public String getTaskOverviewCssSheet() {
        return taskOverviewCssSheet;
    }

    public String getTaskOverviewAddTaskFxml() { return "/screens/MainScreen/TasksOverview/TaskOverviewAddTaskDesign.fxml";}

    public String getTaskOverviewAddTaskSheet() {
        return taskOverviewAddTaskSheet;
    }

    public String getUserProfileScreenFxml(){return"/screens/userProfileScreen/UserProfileDesign.fxml"; }

    public String getUserProfileScreenCssSheet(){return userProfileScreenCssSheet; }

    public String getEditTaskScreenCssSheet() {
        return editTaskScreenCssSheet;
    }

    public String getEditTaskScreenFxml(){
        return "/screens/EditTaskScreen/EditTaskScreenDesign.fxml";
    }

    public String getAddTaskScreenFxml() {
        return "/screens/AddTaskScreen/AddTaskScreenDesign.fxml";
    }

    public String getAddTaskScreenCssSheet() {
        return addTaskScreenCssSheet;
    }

    public String getAddTaskNoteComponentFxml(){return "/screens/AddTaskScreen/NoteComponent.fxml";}

    public String getAddTaskNoteComponentCssSheet() {
        return addTaskNoteComponentCssSheet;
    }

    public String getEditTaskNoteComponentFxml() {return "/screens/EditTaskScreen/NoteComponent.fxml";}

    public String getEditTaskNoteComponentCssSheet() {return editTaskNoteComponentCssSheet;}

}
