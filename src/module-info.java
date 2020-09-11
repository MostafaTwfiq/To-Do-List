module ToDoList {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens Main;
    opens GUI.Screen;

}