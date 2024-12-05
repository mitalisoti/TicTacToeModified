// File: TopPanel.java
// File: TopPanel.java
/*
package org.example.tictactoefx;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.tictactoefx.GameController;

import java.util.function.Consumer;

public class TopPanel {
    private final VBox layout;
    private final ComboBox<String> themeSelector;
    private final ComboBox<String> modeSelector;
    private final ComboBox<String> difficultySelector;

    private Button resetButton;

    private GameController gameController;

    public TopPanel() {
        layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        resetButton = new Button("Reset");
        resetButton.setOnAction(e -> gameController.resetGame());


        themeSelector = new ComboBox<>();
        themeSelector.getItems().addAll("Default", "Light", "Dark", "Blue");
        themeSelector.setValue("Default");

        modeSelector = new ComboBox<>();
        modeSelector.getItems().addAll("Single Player", "Two Players");
        modeSelector.setValue("Single Player");

        difficultySelector = new ComboBox<>();
        difficultySelector.getItems().addAll("Easy", "Medium", "Hard");
        difficultySelector.setValue("Easy");



        HBox controls = new HBox(10, themeSelector, modeSelector, difficultySelector);
        controls.setAlignment(Pos.CENTER);

        layout.getChildren().add(controls);
    }

    public VBox getLayout() {
        return layout;
    }
    public Pane getLayout2() {
        // Set up the layout including reset button
        HBox topLayout = new HBox();
        topLayout.getChildren().add(resetButton);
        return topLayout;
    }


    public void setGameController(GameController gameController) {
        this.gameController = gameController;

        modeSelector.setOnAction(event -> gameController.setMode(modeSelector.getValue()));
        difficultySelector.setOnAction(event -> gameController.setDifficulty(difficultySelector.getValue()));
    }

    public void setThemeChangeListener(Consumer<String> themeChangeListener) {
        themeSelector.setOnAction(event -> themeChangeListener.accept(themeSelector.getValue()));
    }
}

 */
