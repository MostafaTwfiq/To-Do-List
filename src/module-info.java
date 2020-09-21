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
    opens JFoenix;

    //Light mode theme:
    opens GUI.Style.ThemesCss.LightTheme.Stage;
    opens GUI.Style.ThemesCss.LightTheme.LoginScreen;
    opens GUI.Style.ThemesCss.LightTheme.SignUpScreen;

    //Dark mode theme
    opens GUI.Style.ThemesCss.DarkTheme.Stage;
    opens GUI.Style.ThemesCss.DarkTheme.LoginScreen;
    opens GUI.Style.ThemesCss.DarkTheme.SignUpScreen;

}