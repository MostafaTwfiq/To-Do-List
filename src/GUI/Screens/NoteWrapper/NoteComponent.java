package GUI.Screens.NoteWrapper;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class NoteComponent extends Pane {

    private final double HEIGHT = 196.0;
    private final double WIDTH = 472.0;

    private NoteComponent noteContainer;

    private VBox mainBody = new VBox();
    private Label paddingLbl = new Label();
    private Label paddingLbl2 = new Label();
    private BorderPane borderPane = new BorderPane();


    private JFXButton deleteButton = new JFXButton("X");
    private TextArea txtArea = new TextArea();
    private HBox txtFldAndBtnContainer =  new HBox();

    private boolean updated  = false;

    public NoteComponent(NoteComponent noteComponent){

        super();
        noteContainer = noteComponent;

        this.setPrefHeight(HEIGHT);
        this.setPrefWidth(WIDTH);
        this.setStyle("-fx-background-radius: 10; " +
                      "-fx-border-radius: 10;");

        mainBody.setPrefHeight(this.getHeight());
        mainBody.setPrefWidth(this.getWidth()*461.0/this.WIDTH);

        mainBody.setLayoutX(6.0);
        mainBody.setLayoutY(-2.0);

        borderPane.setPrefHeight(this.getHeight()*200.0/this.HEIGHT);
        borderPane.setPrefWidth(this.getWidth()*200.0/this.WIDTH);

        txtArea.setPrefHeight(this.getHeight()*126.0/this.HEIGHT);
        txtArea.setPrefWidth(this.getWidth()*443.0/this.WIDTH);

        borderPane.getChildren().add(txtArea);
        borderPane.setAlignment(txtArea, Pos.CENTER);
        borderPane.setCenter(txtArea);


        txtFldAndBtnContainer.prefHeight(this.getHeight()*26.0/this.HEIGHT);
        txtFldAndBtnContainer.prefWidth(this.getWidth()*462/this.WIDTH);

        paddingLbl2.setPrefHeight(this.getWidth()*18.0/this.HEIGHT);
        paddingLbl2.setPrefWidth(this.getWidth()*415.0/this.WIDTH);
        deleteButton.setFont(new Font("Arial",12.0));

        txtFldAndBtnContainer.getChildren().addAll(deleteButton,paddingLbl2);

        borderPane.setTop(txtFldAndBtnContainer);

        mainBody.getChildren().add(borderPane);
        paddingLbl.setPrefHeight(this.getHeight() * 12.0/this.HEIGHT);
        paddingLbl.setPrefWidth(this.getWidth()*460.0/this.WIDTH);


        mainBody.getChildren().add(paddingLbl);
        this.getChildren().add(mainBody);
    }
}
