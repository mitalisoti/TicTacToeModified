package org.example.tictactoefx;

//import javafx.scene.media.Media;

//import javafx.scene.media.MediaPlayer;


import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;




public class HelloApplication extends Application {

    private final BorderPane borderPane = new BorderPane();
    private final GridPane gridPane = new GridPane();
    private final Label title = new Label("Tic-Tac-Toe");
    private final Button restartButton = new Button("Restart");
    private final ComboBox<String> themeSelector = new ComboBox<>();
    private final Label activePlayerLabel = new Label("Your Turn");

    private final String[] themes = {"Default", "Light", "Blue", "Dark"};
    private final List<Timeline> activeAnimations = new ArrayList<>();
    private final Button[] btns = new Button[9];
    private final int[] gameState = {3, 3, 3, 3, 3, 3, 3, 3, 3};
   // String audioFilePath = Objects.requireNonNull(getClass().getResource("/org/example/tictactoefx/sound/win.mp3")).toExternalForm();


    private boolean gameOver = false;
    private int activePlayer = 0;
    private static final int EMPTY = 3;
    private static final int PLAYER_ONE = 0;// Player uses O
    private static final int PLAYER_TWO = 1;
    private static final int AI = 1;     // AI uses X

    private final int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6}             // Diagonals
    };
    private final Image xImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/tictactoefx/x.png")));
    private final Image oImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/tictactoefx/o.png")));
    private final ComboBox<String> difficultSelector = new ComboBox<>();
    private final String[] difficultyLevels = {"Easy", "Medium", "Hard"};
    private final ComboBox<String> modeSelector = new ComboBox<>();
   // private MediaPlayer winSoundPlayer;
   // private MediaPlayer drawSoundPlayer;
   // private MediaPlayer clickSoundPlayer;



    @Override
    public void start(Stage stage) {
        //AudioFileFormat winSound = new AudioFileFormat();
        try {
            //Media winSound = new Media(Objects.requireNonNull(getClass().getResource("/org/example/tictactoefx/sounds/win.wav")).toExternalForm());
         //   Media drawSound = new Media(Objects.requireNonNull(getClass().getResource("/org/example/tictactoefx/sounds/draw.wav")).toExternalForm());
           // Media clickSound = new Media(Objects.requireNonNull(getClass().getResource("/org/example/tictactoefx/sounds/click.wav")).toExternalForm());

           // winSoundPlayer = new MediaPlayer(winSound);
         //   drawSoundPlayer = new MediaPlayer(drawSound);
          //  clickSoundPlayer = new MediaPlayer(clickSound);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading audio files.");
        }

        createGUI();
        handleEvents();
        Scene scene = new Scene(borderPane, 600, 700);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/example/tictactoefx/style.css")).toExternalForm());


        scene.setCursor(Cursor.HAND);
        stage.setTitle("Tic-Tac-Toe");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/tictactoefx/unnamed.png"))));
        stage.setScene(scene);
        stage.show();
        String resourcePath = "sound/win.mp3";
        String fullPath = String.valueOf(HelloApplication.class.getResource(resourcePath));
        AudioClip audioClip = new AudioClip(fullPath);
        audioClip.setVolume(0.5);
        audioClip.play();

    }

    private void createGUI() {
        //title styling
        Font font = Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 30);
        title.setFont(font);
        title.setTextFill(Color.DARKBLUE);
        title.setUnderline(true);
        title.setEffect(new DropShadow());
        DropShadow shadow = new DropShadow();
        shadow.setRadius(5);
        shadow.setColor(Color.LIGHTBLUE);
        title.setEffect(shadow);

        //Theme selection style
        themeSelector.getItems().addAll(themes);
        themeSelector.setValue(themes[0]);
        themeSelector.setStyle("-fx-font-weight: bold;");

        themeSelector.setOnAction(event -> applyTheme(themeSelector.getValue()));

        difficultSelector.getItems().addAll(difficultyLevels);
        difficultSelector.setValue(difficultyLevels[0]);
        difficultSelector.setStyle("-fx-font-weight: bold;");

        modeSelector.getItems().addAll("Single Player", "Two Players");
        modeSelector.setValue("Single Player");
        modeSelector.setStyle("-fx-font-weight: bold;");

        Label spacer = new Label();
        spacer.setMinHeight(20);

        HBox selectors = new HBox(20, themeSelector, modeSelector, difficultSelector); // 20 is the spacing between them
        selectors.setAlignment(Pos.CENTER);

        // Top container with title and dropdowns in the same line
        VBox topContainer = new VBox(10, title, selectors, activePlayerLabel);
        topContainer.setAlignment(Pos.CENTER);
        BorderPane.setMargin(topContainer, new Insets(20));
        borderPane.setTop(topContainer);
        activePlayerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Set font to bold

        VBox.setMargin(spacer, new Insets(0, 0, 20, 0));
        BorderPane.setMargin(topContainer, new Insets(20));
        borderPane.setTop(topContainer);

        restartButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        restartButton.setStyle("-fx-background-color: #14144e; -fx-text-fill: white;");
        restartButton.setDisable(true);
        restartButton.setOnMouseEntered(event -> restartButton.setStyle("-fx-background-color: #14144e; -fx-text-fill: white;"));
        restartButton.setOnMouseExited(event -> restartButton.setStyle("-fx-background-color: #14144e; -fx-text-fill: white;"));
        restartButton.setOnAction(event -> resetGame());
        BorderPane.setMargin(restartButton, new Insets(20));
        borderPane.setBottom(restartButton);
        BorderPane.setAlignment(restartButton, Pos.CENTER);


        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (int i = 0; i < 9; i++) {
            Button button = new Button();
            button.setId(String.valueOf(i));
            button.setPrefSize(150, 150);
            button.getStyleClass().add("game-button");
            gridPane.add(button, i % 3, i / 3);
            btns[i] = button;
        }
        borderPane.setCenter(gridPane);
    }

    private void handleEvents() {
        restartButton.setOnAction(event -> resetGame());

        modeSelector.setOnAction(event -> {
            boolean isSinglePlayer = modeSelector.getValue().equals("Single Player");
            difficultSelector.setDisable(!isSinglePlayer);
        });

        for (Button button : btns) {
            button.setOnAction(event -> {
                if (gameOver || gameState[Integer.parseInt(button.getId())] != EMPTY) {
                    return;
                }
                button.getStyleClass().add("game-button-active");// Highlight the clicked button
                int currentPlayer = activePlayer;
                makeMove(button, currentPlayer);
                if (!gameOver) {
                    if(modeSelector.getValue().equals("Single Player") && activePlayer ==  PLAYER_TWO) {
                        makeRandomMoveWithDelay();
                    }
                }
            });
        }
    }


    private void makeMove(Button button, int player) {
        int id = Integer.parseInt(button.getId());
        gameState[id] = player;
        button.setGraphic(new ImageView(player == PLAYER_ONE ? oImage : xImage));
        button.setDisable(true);
        checkForWinner();
        checkForDraw();

        activePlayer = (player == PLAYER_ONE) ? PLAYER_TWO : PLAYER_ONE;
    }

    private void checkForWinner() {
        for (int[] wp : winningPositions) {
            if (gameState[wp[0]] == gameState[wp[1]] &&
                    gameState[wp[1]] == gameState[wp[2]] &&
                    gameState[wp[0]] != EMPTY) {
                gameOver = true;
                showWinnerAlert(gameState[wp[0]]);
                restartButton.setDisable(false);
                return;
            }
        }
    }

    //AI makes a random move
    private int makeRandomMoveWithDelay() {
        List<Integer> availableCells = new ArrayList<>();
        for (int i = 0; i < gameState.length; i++) {
            if (gameState[i] == EMPTY) {
                availableCells.add(i);
            }
        }
        if (availableCells.isEmpty()) return 0;

        disableGameButtons();
        //int cell = availableCells.get((int) (Math.random() * availableCells.size()));
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            if (!gameOver) {
                int cell = getAIMove(availableCells);
                makeMove(btns[cell], PLAYER_TWO);
            }
            enableGameButtons();
        });
        pause.play();
        return 0;
    }

    //HArd AI: Tries to win, blocks opponent, or picks a random move
    private int hardAI(List<Integer> availableCells) {
        // Try to win
        for (int index : availableCells) {
            if (canWin(PLAYER_TWO, index)) {
                return index; // Make the winning move
            }
        }

        // Try to block the player from winning
        for (int index : availableCells) {
            if (canWin(PLAYER_ONE, index)) {
                return index; // Block the opponent's winning move
            }
        }

        // Prioritize the center
        if (availableCells.contains(4)) {
            return 4; // Take the center if available
        }

        // Prioritize corners
        List<Integer> corners = Arrays.asList(0, 2, 6, 8);
        for (int corner : corners) {
            if (availableCells.contains(corner)) {
                return corner;
            }
        }

        // Otherwise, pick a random move
        return availableCells.get((int) (Math.random() * availableCells.size()));
    }


    private boolean canWin(int player, int index) {
        gameState[index] = player; //temp move

        boolean canWin = false;

        for (int[] wp : winningPositions) {
            if (gameState[wp[0]] == player &&
                    gameState[wp[1]] == player &&
                    gameState[wp[2]] == player) {
                canWin = true;
                break;
            }
        }

        // Undo the move
        gameState[index] = EMPTY;
        return canWin;

    }
    private int mediumAI(List<Integer> availableCells) {
        if (Math.random() < 0.5) {
            return hardAI(availableCells); // Half the time use Hard logic
        } else {
            return availableCells.get((int) (Math.random() * availableCells.size()));
        }
    }

    private void disableGameButtons() {
        for (Button button : btns) {
            button.setDisable(true);
        }
    }

    private void enableGameButtons() {
        for (Button button : btns) {
            if (gameState[Integer.parseInt(button.getId())] == EMPTY) {
                button.setDisable(false);
            }
        }
    }

    private void updateButtonGraphic(Button button, int player) {
        button.setStyle("-fx-font-size: 18px; -fx-border-color: gray; -fx-background-color: transparent;");

        button.setGraphic(new ImageView(player == AI ? xImage : oImage));  // Set image after background
    }

    private void showWinnerAlert(int player) {
        String winner = (player == PLAYER_ONE) ? "Player 1" : (modeSelector.getValue().equals("Single Player") ? "AI" : "Player 2");
        showAlert("Game Over", winner + " wins!");
    }

    private void checkForDraw() {
        if (!gameOver && Arrays.stream(gameState).noneMatch(state -> state == EMPTY)) {
            gameOver = true;
            showAlert("Draw", "The game is a draw!");
            restartButton.setDisable(false);
        }
    }


/*
    private void playWinSound() {
        try {
            String audioFilePath = Objects.requireNonNull(getClass().getResource("/org/example/tictactoe/sounds/win.wav")).toExternalForm();
            Media sound = new Media(audioFilePath);
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: Audio playback failed.");
        }
    }

 */

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.show();
    }

    private void highlightWinningButtons(int[] wp) {
        for (int index : wp) {
            Button button = btns[index];
            button.setDisable(false);  // Ensure button is enabled during highlighting
            button.getStyleClass().add("game-button-winning");


            // Debugging: print the current styles of the button
            System.out.println("Button " + index + " styles: " + button.getStyleClass());

            // Remove the 'game-button-winning' class after a short delay
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(5), e -> {
                        button.getStyleClass().remove("game-button-winning");
                        System.out.println("Button " + index + " styles after reset: " + button.getStyleClass());
                    })
            );
            timeline.play();
        }
    }
/*
    private Timeline createCellAnimation(Button button) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> button.getStyleClass().add("game-button-winning")),
                new KeyFrame(Duration.seconds(1), e -> button.getStyleClass().remove("game-button-winning"))
        );
        timeline.setCycleCount(5);
        timeline.setAutoReverse(false);
        activeAnimations.add(timeline);
        return timeline;
    }

 */

    private void resetGame() {
        activeAnimations.forEach(Timeline::stop);
        activeAnimations.clear();
        Arrays.fill(gameState, EMPTY);

        for (Button button : btns) {
            button.setGraphic(null);
            button.setStyle("-fx-font-size: 18px; -fx-background-color: transparent; -fx-border-color: gray;");
            button.setDisable(false);
        }

        gameOver = false;
        activePlayer = PLAYER_ONE;
        activePlayerLabel.setText("Player X's Turn");
        restartButton.setDisable(true);
    }


    private void applyTheme(String theme) {
        // Remove all previous theme classes
        borderPane.getStyleClass().removeAll("default-theme", "dark-theme", "light-theme", "blue-theme");

        switch (theme) {
            case "Default":
                borderPane.getStyleClass().add("default-theme");
                break;
            case "Dark":
                borderPane.getStyleClass().add("dark-theme");
                break;
            case "Light":
                borderPane.getStyleClass().add("light-theme");
                break;
            case "Blue":
                borderPane.getStyleClass().add("blue-theme");
                break;
        }
    }

    private int getAIMove(List<Integer> availableCells) {
        String difficulty = difficultSelector.getValue();
        switch (difficulty) {
            case "Hard":
                return hardAI(availableCells);
            case "Medium":
                return mediumAI(availableCells);
            default: // Easy
                return availableCells.get((int) (Math.random() * availableCells.size()));
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}



