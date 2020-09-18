module ToDoList {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires TrayTester;
    requires com.jfoenix;

    opens Main;
    opens GUI.ScreenManager.Stage;

}