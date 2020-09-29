module ToDoList {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires TrayTester;
    requires com.jfoenix;


    opens Main;
    opens GUI.ScreenManager.Stage;
    opens GUI.Screens.LoginScreen;
    opens GUI.Screens.SignUpScreen;
    opens GUI.Screens.MainScreen;
    opens GUI.Screens.MainScreen.TasksOverview;
    opens GUI.Screens.userProfileScreen;
    opens GUI.Screens.AddTaskScreen;
    opens GUI.Screens.EditTaskScreen;
    opens JFoenix;


    //Light mode theme:
    opens GUI.Style.ThemesCss.LightTheme.Stage;
    opens GUI.Style.ThemesCss.LightTheme.LoginScreen;
    opens GUI.Style.ThemesCss.LightTheme.SignUpScreen;
    opens GUI.Style.ThemesCss.LightTheme.MainScreen;
    opens GUI.Style.ThemesCss.LightTheme.UserProfileScreen;
    opens GUI.Style.ThemesCss.LightTheme.AddTaskScreen;
    opens GUI.Style.ThemesCss.LightTheme.EditTaskScreen;


    //Dark mode theme:
    opens GUI.Style.ThemesCss.DarkTheme.Stage;
    opens GUI.Style.ThemesCss.DarkTheme.LoginScreen;
    opens GUI.Style.ThemesCss.DarkTheme.SignUpScreen;
    opens GUI.Style.ThemesCss.DarkTheme.UserProfileScreen;
    opens GUI.Style.ThemesCss.DarkTheme.MainScreen;
    opens GUI.Style.ThemesCss.DarkTheme.AddTaskScreen;
    opens GUI.Style.ThemesCss.DarkTheme.EditTaskScreen;

    //Purk mode theme:
    opens GUI.Style.ThemesCss.PurkTheme.Stage;
    opens GUI.Style.ThemesCss.PurkTheme.LoginScreen;
    opens GUI.Style.ThemesCss.PurkTheme.SignUpScreen;
    opens GUI.Style.ThemesCss.PurkTheme.UserProfileScreen;
    opens GUI.Style.ThemesCss.PurkTheme.MainScreen;
    opens GUI.Style.ThemesCss.PurkTheme.AddTaskScreen;
    opens GUI.Style.ThemesCss.PurkTheme.EditTaskScreen;


}