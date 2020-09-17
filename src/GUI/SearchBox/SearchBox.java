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
    private Color textColor;
    private Color promptTextColor;
    private final String defaultSearchIconPath = "resources/searchBoxIcon.png";

    public SearchBox(double h, double w, String promptText,
                     List<IObserver> observers,
                     Color searchBoxColor,
                     Color textColor,
                     Color promptTextColor) {

        if (promptText == null || observers == null
                || searchBoxColor == null || textColor == null
                || promptTextColor == null) {
            throw new IllegalArgumentException();
        }

        this.searchBoxColor = searchBoxColor;
        this.observers = observers;
        this.textColor = textColor;
        this.promptTextColor = promptTextColor;

        setupLayout(h, w);
        setupSearchField(promptText);
        setupSearchIconImageView(defaultSearchIconPath);

        getChildren().addAll(searchIcon, searchField);

    }

    public SearchBox(double h, double w, String promptText,
                     Color searchBoxColor,
                     Color textColor,
                     Color promptTextColor) {

        if (promptText == null
                || searchBoxColor == null || textColor == null
                || promptTextColor == null) {
            throw new IllegalArgumentException();
        }

        this.searchBoxColor = searchBoxColor;
        observers = new ArrayList<>();
        this.textColor = textColor;
        this.promptTextColor = promptTextColor;

        setupLayout(h, w);
        setupSearchField(promptText);
        setupSearchIconImageView(defaultSearchIconPath);

        getChildren().addAll(searchIcon, searchField);

    }

    public SearchBox(double h, double w,
                     String promptText, String searchIconPath,
                     List<IObserver> observers,
                     Color searchBoxColor,
                     Color textColor,
                     Color promptTextColor) {

        if (promptText == null || searchIconPath == null || observers == null
                || searchBoxColor == null || textColor == null
                || promptTextColor == null) {
            throw new IllegalArgumentException();
        }

        this.searchBoxColor = searchBoxColor;
        this.observers = observers;
        this.textColor = textColor;
        this.promptTextColor = promptTextColor;

        setupLayout(h, w);
        setupSearchField(promptText);
        setupSearchIconImageView(searchIconPath);

        getChildren().addAll(searchIcon, searchField);

    }

    public SearchBox(double h, double w,
                     String promptText, String searchIconPath,
                     Color searchBoxColor,
                     Color textColor,
                     Color promptTextColor) {

        if (promptText == null || searchIconPath == null
                || searchBoxColor == null || textColor == null
                || promptTextColor == null) {
            throw new IllegalArgumentException();
        }

        this.searchBoxColor = searchBoxColor;
        observers = new ArrayList<>();
        this.textColor = textColor;
        this.promptTextColor = promptTextColor;

        setupLayout(h, w);
        setupSearchField(promptText);
        setupSearchIconImageView(searchIconPath);

        getChildren().addAll(searchIcon, searchField);

    }

    private void setupLayout(double h, double w) {

        setPrefHeight(h);
        setPrefWidth(w);

        setPadding(new Insets(2, 2, 2, 2));

        setSpacing(2);

        setAlignment(Pos.CENTER_LEFT);

        styleProperty().set("-fx-background-radius: " + getPrefHeight() / 5.0 + ";"
                + "-fx-border-radius: " + getPrefHeight() / 5.0 + ";"
                + "-fx-border-color: transparent;"
                + "-fx-background-color: " + colorToHex(searchBoxColor) + ";");

    }

    private void setupSearchIconImageView(String iconPath) {

        try {

            FileInputStream inputStream = new FileInputStream(iconPath);
            Image image = new Image(inputStream);
            searchIcon = new ImageView(image);

            searchIcon.setFitHeight(getPrefHeight() - getInsets().getTop() - getInsets().getBottom());
            searchIcon.setFitWidth(getPrefHeight() - getInsets().getTop() - getInsets().getBottom());

        } catch (Exception e) {
            System.out.println("Something went wrong while loading search box icon");
        }


    }

    private void setupSearchField(String promptText) {

        searchField = new TextField();

        searchField.setPromptText(promptText);

        searchField.setStyle(getSearchFieldStyle());

        searchField.setPrefHeight(getHeight() - getInsets().getTop() - getInsets().getBottom());
        searchField.setPrefWidth(getPrefWidth() - getPrefHeight() - getSpacing() - getPadding().getLeft());

        searchField.setOnKeyReleased(e -> {

            if (e.getCode() == KeyCode.ENTER) {
                notifyObservers();
            }

        });

    }

    private String getSearchFieldStyle() {
        return    "-fx-border-color: transparent;"
                + "-fx-background-color: transparent;"
                + "-fx-text-fill: " + colorToHex(textColor) + ";"
                + "-fx-prompt-text-fill: " + colorToHex(promptTextColor) + ";";
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
