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
    opens JFoenix;

    //Light mode theme:
    opens GUI.Style.ThemesCss.LightTheme.Stage;
    opens GUI.Style.ThemesCss.LightTheme.LoginScreen;
    opens GUI.Style.ThemesCss.LightTheme.SignUpScreen;
    opens GUI.Style.ThemesCss.LightTheme.MainScreen;

    //Dark mode theme
    opens GUI.Style.ThemesCss.DarkTheme.Stage;
    opens GUI.Style.ThemesCss.DarkTheme.LoginScreen;
    opens GUI.Style.ThemesCss.DarkTheme.SignUpScreen;
    opens GUI.Style.ThemesCss.DarkTheme.MainScreen;

}