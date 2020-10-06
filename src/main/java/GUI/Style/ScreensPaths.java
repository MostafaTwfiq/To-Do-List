package GUI.Style;

import Main.Main;

public class ScreensPaths {

    private final String stageCssSheet = getClass().getResource("/ThemesCss/" + Main.theme.getThemeName() + "/Stage/StageSheet.css").toExternalForm();
    private final String loginScreenCssSheet = getClass().getResource("/ThemesCss/" + Main.theme.getThemeName() + "/LoginScreen/LoginSheet.css").toExternalForm();
    private final String signUpScreenCssSheet = getClass().getResource("/ThemesCss/" + Main.theme.getThemeName() + "/SignUpScreen/SignUpSheet.css").toExternalForm();
    private final String mainScreenCssSheet = getClass().getResource("/ThemesCss/" + Main.theme.getThemeName() + "/MainScreen/AppMainSheet.css").toExternalForm();
    private final String taskOverviewCssSheet = getClass().getResource("/ThemesCss/" + Main.theme.getThemeName() + "/MainScreen/TaskOverviewSheet.css").toExternalForm();
    private final String taskOverviewAddTaskSheet = getClass().getResource("/ThemesCss/" + Main.theme.getThemeName() + "/MainScreen/TaskOverviewAddTaskSheet.css").toExternalForm();
    private final String userProfileScreenCssSheet = getClass().getResource("/ThemesCss/" + Main.theme.getThemeName()+ "/UserProfileScreen/UserProfileScreen.css").toExternalForm();
    private final String editTaskScreenCssSheet = getClass().getResource("/ThemesCss/" + Main.theme.getThemeName() + "/EditTaskScreen/EditTaskScreen.css").toExternalForm();
    private final String addTaskScreenCssSheet  = getClass().getResource("/ThemesCss/" + Main.theme.getThemeName() + "/AddTaskScreen/AddTaskScreen.css").toExternalForm();
    private final String addTaskNoteComponentCssSheet = getClass().getResource("/ThemesCss/" + Main.theme.getThemeName() + "/AddTaskScreen/NoteComponent.css").toExternalForm();
    private final String editTaskNoteComponentCssSheet = getClass().getResource("/ThemesCss/" + Main.theme.getThemeName() + "/EditTaskScreen/NoteComponent.css").toExternalForm();

    public String getStageFxml() {return "/Screens/Stage/StageDesign.fxml"; }

    public String getStageCssSheet() {
        return stageCssSheet;
    }

    public String getSplashScreenFxml() {return "/Screens/SplashScreen/SplashScreenDesign.fxml"; }

    public String getLoginScreenFxml() {
        return "/Screens/LoginScreen/LoginScreenStyle.fxml";
    }

    public String getLoginScreenCssSheet() {
        return loginScreenCssSheet;
    }

    public String getSignUpScreenFxml() {
        return "/Screens/SignUpScreen/SignUpScreenDesign.fxml";
    }

    public String getSignUpScreenCssSheet() {
        return signUpScreenCssSheet;
    }

    public String getMainScreenFxml() {
        return "/Screens/MainScreen/MainScreenDesign.fxml";
    }

    public String getMainScreenCssSheet() {
        return mainScreenCssSheet;
    }

    public String getTaskOverviewFxml() {
        return "/Screens/MainScreen/TasksOverview/TaskOverviewDesign.fxml";
    }

    public String getTaskOverviewCssSheet() {
        return taskOverviewCssSheet;
    }

    public String getTaskOverviewAddTaskFxml() { return "/Screens/MainScreen/TasksOverview/TaskOverviewAddTaskDesign.fxml";}

    public String getTaskOverviewAddTaskSheet() {
        return taskOverviewAddTaskSheet;
    }

    public String getUserProfileScreenFxml(){return"/Screens/userProfileScreen/UserProfileDesign.fxml"; }

    public String getUserProfileScreenCssSheet(){return userProfileScreenCssSheet; }

    public String getEditTaskScreenCssSheet() {
        return editTaskScreenCssSheet;
    }

    public String getEditTaskScreenFxml(){
        return "/Screens/EditTaskScreen/EditTaskScreenDesign.fxml";
    }

    public String getAddTaskScreenFxml() {
        return "/Screens/AddTaskScreen/AddTaskScreenDesign.fxml";
    }

    public String getAddTaskScreenCssSheet() {
        return addTaskScreenCssSheet;
    }

    public String getAddTaskNoteComponentFxml(){return "/Screens/AddTaskScreen/NoteComponent.fxml";}

    public String getAddTaskNoteComponentCssSheet() {
        return addTaskNoteComponentCssSheet;
    }

    public String getEditTaskNoteComponentFxml() {return "/Screens/EditTaskScreen/NoteComponent.fxml";}

    public String getEditTaskNoteComponentCssSheet() {return editTaskNoteComponentCssSheet;}

}
