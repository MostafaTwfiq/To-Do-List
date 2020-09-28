module ToDoList {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires TrayTester;
    requires com.jfoenix;
    requires org.jetbrains.annotations;


    opens Main;
    opens GUI.ScreenManager.Stage;
    opens GUI.Screens.LoginScreen;
    opens GUI.Screens.SignUpScreen;
    opens GUI.Screens.MainScreen;
    opens GUI.Screens.MainScreen.TasksOverview;
    opens GUI.Screens.userProfileScreen;
    opens JFoenix;


    //Light mode theme:
    opens GUI.Style.ThemesCss.LightTheme.Stage;
    opens GUI.Style.ThemesCss.LightTheme.LoginScreen;
    opens GUI.Style.ThemesCss.LightTheme.SignUpScreen;
    opens GUI.Style.ThemesCss.LightTheme.MainScreen;
    opens GUI.Style.ThemesCss.LightTheme.UserProfileScreen;


    //Dark mode theme:
    opens GUI.Style.ThemesCss.DarkTheme.Stage;
    opens GUI.Style.ThemesCss.DarkTheme.LoginScreen;
    opens GUI.Style.ThemesCss.DarkTheme.SignUpScreen;
    opens GUI.Style.ThemesCss.DarkTheme.UserProfileScreen;
    opens GUI.Style.ThemesCss.DarkTheme.MainScreen;


    //Purk mode theme:
    opens GUI.Style.ThemesCss.PurkTheme.Stage;
    opens GUI.Style.ThemesCss.PurkTheme.LoginScreen;
    opens GUI.Style.ThemesCss.PurkTheme.SignUpScreen;
    opens GUI.Style.ThemesCss.PurkTheme.UserProfileScreen;
    opens GUI.Style.ThemesCss.PurkTheme.MainScreen;


}