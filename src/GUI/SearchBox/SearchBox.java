package GUI.SearchBox;

import GUI.Observer.IObserver;
import GUI.Style.ColorTransformer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SearchBox extends HBox {

    private TextField searchField;
    private ImageView searchIcon;
    private Color searchBoxColor;
    private Color textColor;
    private Color promptTextColor;
    private final String defaultSearchIconPath = "resources/SearchBox/searchBoxIcon.png";
    private boolean animationLocker;
    private EventHandler<ActionEvent> onEnterEvent;

    public SearchBox(double h, double w, String promptText,
                     EventHandler<ActionEvent> onEnterEvent,
                     Color searchBoxColor,
                     Color textColor,
                     Color promptTextColor) {

        if (promptText == null || onEnterEvent == null
                || searchBoxColor == null || textColor == null
                || promptTextColor == null) {
            throw new IllegalArgumentException();
        }

        this.searchBoxColor = searchBoxColor;
        this.onEnterEvent = onEnterEvent;
        this.textColor = textColor;
        this.promptTextColor = promptTextColor;
        animationLocker = false;

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
        this.textColor = textColor;
        this.promptTextColor = promptTextColor;
        animationLocker = false;

        setupLayout(h, w);
        setupSearchField(promptText);
        setupSearchIconImageView(defaultSearchIconPath);


        if (searchIcon != null)
            getChildren().add(searchIcon);

        getChildren().add(searchField);

    }

    public SearchBox(double h, double w,
                     String promptText, String searchIconPath,
                     EventHandler<ActionEvent> onEnterEvent,
                     Color searchBoxColor,
                     Color textColor,
                     Color promptTextColor) {

        if (promptText == null || searchIconPath == null || onEnterEvent == null
                || searchBoxColor == null || textColor == null
                || promptTextColor == null) {
            throw new IllegalArgumentException();
        }

        this.searchBoxColor = searchBoxColor;
        this.onEnterEvent = onEnterEvent;
        this.textColor = textColor;
        this.promptTextColor = promptTextColor;
        animationLocker = false;

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
        this.textColor = textColor;
        this.promptTextColor = promptTextColor;
        animationLocker = false;

        setupLayout(h, w);
        setupSearchField(promptText);
        setupSearchIconImageView(searchIconPath);

        getChildren().addAll(searchIcon, searchField);

    }

    public void changeWidth(double w) {
        setPrefWidth(w);

        searchField.setPrefWidth(getPrefWidth() - getPrefHeight() - getSpacing() - getPadding().getLeft());

    }

    public void changeHeight(double h) {

        setPrefHeight(h);

        if (searchIcon != null) {
            searchIcon.setFitHeight(getPrefHeight() - getInsets().getTop() - getInsets().getBottom());
            searchIcon.setFitWidth(getPrefHeight() - getInsets().getTop() - getInsets().getBottom());
        }

        searchField.setPrefHeight(getHeight() - getInsets().getTop() - getInsets().getBottom());

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
                + "-fx-background-color: " + new ColorTransformer().colorToHex(searchBoxColor) + ";"
                + "-fx-border-color: " + new ColorTransformer().colorToHex(textColor) + ";"
                + "-fx-border-width: 0.3;"
        );

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

                if (searchIcon != null && onEnterEvent != null) {
                    activateIconAnimation();
                    onEnterEvent.handle(null);
                }

            }

        });

    }

    private void activateIconAnimation() {

        if (animationLocker)
            return;
        else
            animationLocker = true;

        KeyValue heightKeyVal = new KeyValue(searchIcon.fitHeightProperty(), searchIcon.getFitHeight() * 1.5);
        KeyValue widthKeyVal = new KeyValue(searchIcon.fitWidthProperty(), searchIcon.getFitWidth() * 1.5);

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(100), heightKeyVal, widthKeyVal));

        animation.setAutoReverse(true);
        animation.setCycleCount(2);
        animation.setOnFinished(e -> animationLocker = false);

        animation.play();

    }

    private String getSearchFieldStyle() {
        ColorTransformer colorTransformer = new ColorTransformer();
        return    "-fx-border-color: transparent;"
                + "-fx-background-color: transparent;"
                + "-fx-text-fill: " + colorTransformer.colorToHex(textColor) + ";"
                + "-fx-prompt-text-fill: " + colorTransformer.colorToHex(promptTextColor) + ";";
    }


    public String getText() {
        return searchField.getText();
    }

    public void setText(String text) {

        if (text == null)
            throw new IllegalArgumentException();

        searchField.setText(text);

    }

}
