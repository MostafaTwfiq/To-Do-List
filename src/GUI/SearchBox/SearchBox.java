package GUI.SearchBox;

import GUI.Observer.IObserver;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SearchBox extends HBox {

    private List<IObserver> observers;

    private TextField searchField;
    private ImageView searchIcon;
    private Color searchBoxColor;

    public SearchBox(double h, double w, String promptText, Color searchBoxColor) {

        this.searchBoxColor = searchBoxColor;
        observers = new ArrayList<>();

        setupLayout(h, w);
        setupSearchField(promptText);
        setupSearchIconImageView();

        getChildren().addAll(searchIcon, searchField);

    }

    public SearchBox(double h, double w, Color searchBoxColor) {

        this.searchBoxColor = searchBoxColor;
        observers = new ArrayList<>();

        setupLayout(h, w);
        setupSearchField("Search");
        setupSearchIconImageView();

        getChildren().addAll(searchIcon, searchField);

    }

    private void setupLayout(double h, double w) {

        setPrefHeight(h);
        setPrefWidth(w);

        setPadding(new Insets(1, 1, 1, 1));

        setSpacing(1);

        setAlignment(Pos.CENTER_LEFT);

        styleProperty().set("-fx-background-radius: " + getPrefHeight() / 5.0 + ";"
                + "-fx-border-radius: " + getPrefHeight() / 5.0 + ";"
                + "-fx-border-color: transparent;"
                + "-fx-background-color: " + colorToHex(searchBoxColor) + ";");

    }

    private void setupSearchIconImageView() {

        try {

            FileInputStream inputStream = new FileInputStream("resources/searchBoxIcon.png");
            Image image = new Image(inputStream);
            searchIcon = new ImageView(image);

            searchIcon.setFitHeight(getPrefHeight());
            searchIcon.setFitWidth(getPrefHeight());

        } catch (Exception e) {
            System.out.println("Something went wrong while loading search box icon");
        }


    }

    private void setupSearchField(String promptText) {

        searchField = new TextField();

        searchField.setPromptText(promptText);

        searchField.setStyle(getSearchFieldStyle());

        searchField.setPrefHeight(getHeight());
        searchField.setPrefWidth(getPrefWidth() - getPrefHeight() - getSpacing() - getPadding().getLeft());

        searchField.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                notifyObservers();
                System.out.println(searchField.getText());
            }

        });

    }

    private String getSearchFieldStyle() {
        return    "-fx-background-radius: " + getPrefHeight() / 5.0 + ";"
                + "-fx-border-radius: " + getPrefHeight() / 5.0 + ";"
                + "-fx-border-color: transparent;"
                + "-fx-background-color: transparent;";
    }

    private String colorToHex(Color color) {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }


    public String getText() {
        return searchField.getText();
    }

    public void setText(String text) {

        if (text == null)
            throw new IllegalArgumentException();

        searchField.setText(text);

    }

    public void addObserver(IObserver observer) {

        if (observer == null)
            throw new IllegalArgumentException();

        observers.add(observer);

    }

    public void deleteObserver(IObserver observer) {

        if (observer == null)
            throw new IllegalArgumentException();

        observers.remove(observer);

    }

    private void notifyObservers() {

        for (IObserver observer : observers)
            observer.update();

    }

    public List<IObserver> getObservers() {
        return observers;
    }

    public void setObservers(Vector<IObserver> observers) {

        if (observers == null)
            throw new IllegalArgumentException();

        this.observers = observers;
    }

}
