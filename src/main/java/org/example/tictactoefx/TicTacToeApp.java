package org.example.tictactoefx;
import com.sun.javafx.binding.LongConstant;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TicTacToeApp extends Application {

    //UI Components
    private final BorderPane borderPane = new BorderPane();
    private final GridPane gridPane = new GridPane();
    private final Label title = new Label("Tic-Tac-Toe");
    public final Button restartButton = new Button("Restart");
    private final Button feedbackButton = new Button("Rate the Game");
    public Label activePlayerLabel = new Label("Player 1 to play");
    private final String[] themes = {"Default", "Light", "Gradient", "Dark"};

    //Game-specific components
    public Button[] btns = new Button[9];
    public int[] gameState = {3, 3, 3, 3, 3, 3, 3, 3, 3};
    public boolean gameOver = false;
    public int activePlayer = 0;
    private static final int EMPTY = 3;
    public static final int PLAYER_ONE = 0;// Player uses O
    private static final int PLAYER_TWO = 1;
    private static final int AI = 1;     // AI uses X
    private final int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6} };            // Diagonals
    private final Image xImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/tictactoefx/x.png")));
    private final Image oImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/tictactoefx/o.png")));

    //different selectors(drop downs)
    private final ComboBox<String> themeSelector = new ComboBox<>();
    private final ComboBox<String> difficultSelector = new ComboBox<>();
    private final String[] difficultyLevels = {"Easy", "Medium", "Hard"};
    private final ComboBox<String> modeSelector = new ComboBox<>();
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private boolean isMuted;//mute toggle sound effect

    @Override
    public void start(Stage stage) {
        createGUI(); //build UI
        handleEvents(); //set up event handlers
        Scene scene = new Scene(borderPane, 700, 700); //main scene
        borderPane.setCenter(gridPane); // This should be setting the center of BorderPane correctly
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/example/tictactoefx/style.css")).toExternalForm());
        scene.setCursor(Cursor.HAND);
        stage.setTitle("Tic-Tac-Toe");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/tictactoefx/unnamed.png"))));
        stage.setScene(scene);
        stage.show();
    }
    //Creates UI & applies styles
    private void createGUI() {
        //title styling
        Font font = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 30);
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
        themeSelector.setValue(themes[1]);
        themeSelector.setStyle("-fx-font-weight: bold; -fx-background-color: #4CAF50;-fx-text-fill: white;-fx-border-color: #14144e");
        themeSelector.setOnAction(event -> applyTheme(themeSelector.getValue()));
        //difficulty style
        difficultSelector.getItems().addAll(difficultyLevels);
        difficultSelector.setValue(difficultyLevels[0]);
        difficultSelector.setStyle("-fx-font-weight: bold; -fx-background-color: #4CAF50;-fx-text-fill: white;-fx-border-color: #14144e");
        //mode styling
        modeSelector.getItems().addAll("Single Player", "Two Players");
        modeSelector.setValue("Single Player");
        modeSelector.setStyle("-fx-font-weight: bold; -fx-background-color: #4CAF50;-fx-text-fill: white;-fx-border-color: #14144e");

        Slider volumeSlider = new Slider(0, 1, 0.5);  // Min value = 0, Max value = 1, Default value = 0.5 (50%)
        volumeSlider.setBlockIncrement(0.1);
        volumeSlider.setMajorTickUnit(0.2);
        volumeSlider.setMinorTickCount(1);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setStyle("-fx-background-color: #4CAF50;");

        //mute button styling
        Button muteButton = new Button("Mute");
        muteButton.setStyle("-fx-background-color: #14144e; -fx-text-fill: white;");
        muteButton.setOnAction(event -> {
            isMuted = !isMuted;  // Toggle the mute state
            muteButton.setText(isMuted ? "Unmute" : "Mute");  // Change button text
            volumeSlider.setValue(isMuted ? 0 : 0.5);  // Set slider to 0 (mute) or 50% volume when unmuted
        });

        Label spacer = new Label();
        spacer.setMinHeight(20);
        HBox selectors = new HBox(20, themeSelector, modeSelector, difficultSelector, muteButton, volumeSlider); // 20 is the spacing between them
        selectors.setAlignment(Pos.CENTER);
        // Top container with title and dropdowns in the same line
        VBox topContainer = new VBox(10, title, selectors, activePlayerLabel);
        topContainer.setAlignment(Pos.CENTER);
        VBox.setMargin(spacer, new Insets(0, 0, 20, 0));
        BorderPane.setMargin(topContainer, new Insets(20));
        borderPane.setTop(topContainer);
        BorderPane.setMargin(topContainer, new Insets(20));
        borderPane.setTop(topContainer);

        activePlayerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        activePlayerLabel.setStyle("-fx-text-fill: #14144e");
        //Restart and Feedback button

        restartButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        restartButton.setStyle("-fx-background-color: #14144e; -fx-text-fill: white;");
        restartButton.setDisable(true);
        restartButton.setOnMouseEntered(event -> restartButton.setStyle("-fx-background-color: #14144e; -fx-text-fill: white;"));
        restartButton.setOnMouseExited(event -> restartButton.setStyle("-fx-background-color: white; -fx-text-fill: #14144e;"));
        restartButton.setOnAction(event -> resetGame());
        BorderPane.setMargin(restartButton, new Insets(20));
        borderPane.setBottom(restartButton);
        BorderPane.setAlignment(restartButton, Pos.CENTER);
        feedbackButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        feedbackButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        feedbackButton.setOnMouseEntered(event -> feedbackButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"));
        feedbackButton.setOnMouseExited(event-> feedbackButton.setStyle("-fx-background-color: white; -fx-text-fill: #4CAF50;"));
        feedbackButton.setOnAction(event -> openFeedbackDialog());

        HBox bottomContainer = new HBox(10, restartButton, feedbackButton);
        bottomContainer.setAlignment(Pos.CENTER);
        BorderPane.setMargin(bottomContainer, new Insets(20));
        borderPane.setBottom(bottomContainer);
        bottomContainer.setAlignment(Pos.CENTER);
        BorderPane.setMargin(bottomContainer, new Insets(20));
        borderPane.setBottom(bottomContainer);
        //grad pane for the game board
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        //add buttons to the same grid
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

    //Handles events such as button clicks and selector changes.
    private void handleEvents() {
        restartButton.setOnAction(event -> resetGame());

        modeSelector.setOnAction(event -> {
            boolean isSinglePlayer = modeSelector.getValue().equals("Single Player");
            difficultSelector.setDisable(!isSinglePlayer); //no difficulty level if 2 palyers are playing
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

    private void playSound(String resourcePath) {
        if (isMuted) {
            return;  // Exit without playing sound if the game is muted
        }

        // Get the full path of the sound file
        String fullPath = String.valueOf(TicTacToeApp.class.getResource(resourcePath)); // Get full path to the sound resource
        AudioClip audioClip = new AudioClip(fullPath); // Create an audio clip object
        audioClip.setVolume(0.5);
        audioClip.play(); // Play the sound effect
    }

    // Handles a player's move when a button is clicked
    private void makeMove(Button button, int player) {
        int id = Integer.parseInt(button.getId()); // Get the ID of the button to determine its position
        gameState[id] = player; // Update the game state array with the player's move
        button.setGraphic(new ImageView(player == PLAYER_ONE ? oImage : xImage)); // Set the player's symbol (O or X) on the button
        playSound("sound/o.mp3"); // Play a sound effect for the move
        button.setDisable(true); // Disable the button to prevent further interaction
        checkForWinner(); // Check if the move results in a win
        checkForDraw(); // Check if the move results in a draw
        if (gameState[0] != EMPTY) { // If the game has started (any move made)
            // Disable the theme, mode, and difficulty selectors to lock settings
            themeSelector.setDisable(true);
            modeSelector.setDisable(true);
            difficultSelector.setDisable(true);
        }
        // Switch the active player
        activePlayer = (player == PLAYER_ONE) ? PLAYER_TWO : PLAYER_ONE;
        if (!gameOver) { // If the game is not over
            // Update the active player label based on the mode and active player
            if (activePlayer == PLAYER_ONE) {
                activePlayerLabel.setText("Your Turn");
            } else if (modeSelector.getValue().equals("Single Player")) {
                activePlayerLabel.setText("AI's Turn");
            } else {
                activePlayerLabel.setText("Your Turn");
            }
        }
    }

    // Checks if there is a winner based on predefined winning positions
    public void checkForWinner() {
        for (int[] wp : winningPositions) { // Iterate through each set of winning positions
            if (gameState[wp[0]] == gameState[wp[1]] && // Check if the first and second positions match
                    gameState[wp[1]] == gameState[wp[2]] && // Check if the second and third positions match
                    gameState[wp[0]] != EMPTY) { // Ensure the positions are not empty
                gameOver = true; // Mark the game as over
                showWinnerAlert(gameState[wp[0]]); // Display an alert for the winning player
                highlightWinningButtons(wp); // Highlight the buttons in the winning positions
                restartButton.setDisable(false); // Enable the restart button
                playSound("sound/win2.mp3"); // Play a sound effect for winning
                return; // Exit the method as a winner has been determined
            }
        }
    }

    // Makes a random move for the AI player with a slight delay for realism
    private void makeRandomMoveWithDelay() {
        List<Integer> availableCells = new ArrayList<>(); // List to store available cells for AI's move
        for (int i = 0; i < gameState.length; i++) { // Iterate over the game board
            if (gameState[i] == EMPTY) { // Check if the cell is empty
                availableCells.add(i); // Add the empty cell's index to the list
                playSound("sound/x.mp3"); // Play a sound effect for AI move selection
            }
        }
        if (availableCells.isEmpty()) // If no cells are available, return early
            return;

        disableGameButtons(); // Temporarily disable all game buttons during AI's turn

        // Create a pause for 1 second to simulate thinking time
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> { // After the pause
            if (!gameOver) { // If the game is not over
                int cell = getAIMove(availableCells); // Get the AI's chosen move
                makeMove(btns[cell], PLAYER_TWO); // Make the move for the AI
            }
            enableGameButtons(); // Re-enable game buttons after AI's move
        });
        pause.play(); // Start the pause
    }

    //HArd AI: Tries to win, tries to capture center or corners, blocks opponent, or picks a random move
    private int hardAI(List<Integer> availableCells) {
        // Try to win
        for (int index : availableCells) {
            if (canWin(PLAYER_TWO, index)) {
                return index; // Make the winning move
            }}
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
    // Checks if a given player can win by making a move at the specified index
    private boolean canWin(int player, int index) {
        gameState[index] = player; // Temporarily place the player's move at the specified index
        boolean canWin = false; // Initialize the winning condition to false

        for (int[] wp : winningPositions) { // Iterate through all predefined winning positions
            // Check if placing the move at the index completes a winning combination
            if (gameState[wp[0]] == player &&
                    gameState[wp[1]] == player &&
                    gameState[wp[2]] == player) {
                canWin = true; // Set winning condition to true if a winning combination is found
                break; // Exit the loop as winning is confirmed
            }
        }

        // Undo the temporary move to restore the game state
        gameState[index] = EMPTY;
        return canWin; // Return whether the player can win by playing at the specified index
    }

    private int mediumAI(List<Integer> availableCells) {
        if (Math.random() < 0.5) {
            return hardAI(availableCells); // Half the time use Hard logic
        } else {
            return availableCells.get((int) (Math.random() * availableCells.size()));
        }
    }
    // Disables all game buttons to prevent user interaction
    private void disableGameButtons() {
        for (Button button : btns) {
            button.setDisable(true);
        }
    }

    private void enableGameButtons() { //when the button is empty, players can play
        for (Button button : btns) {
            if (gameState[Integer.parseInt(button.getId())] == EMPTY) {
                button.setDisable(false);
            }
        }
    }

    private void showWinnerAlert(int player) {
        String winner = (player == PLAYER_ONE) ? "Player 1" : (modeSelector.getValue().equals("Single Player") ? "AI" : "Player 2");

        showAlert("Game Over", winner + " wins!");
    }

    public void checkForDraw() {
        if (!gameOver && Arrays.stream(gameState).noneMatch(state -> state == EMPTY)) {
            gameOver = true;
            showAlert("Draw", "The game is a draw!");
            playSound("sound/tie.mp3");
            restartButton.setDisable(false);
        }
    }
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

            // Remove the 'game-button-winning' class after a short delay
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(5), e -> {
                        button.getStyleClass().remove("game-button-winning");
                    })
            );
            timeline.play();
        }
    }

    private void resetGame() {
        Arrays.fill(gameState, EMPTY);

        for (Button button : btns) {
            button.setGraphic(null);
            button.getStyleClass().removeAll("game-button-winning", "game-button-active");
            if (!button.getStyleClass().contains("game-button")) {
                button.getStyleClass().add("game-button"); // Reapply default style
            }
            button.setDisable(false);
        }
        gameOver = false;
        activePlayer = PLAYER_ONE;
        activePlayerLabel.setText("Player 1 to play");
        restartButton.setDisable(true);
        modeSelector.setDisable(false);
        difficultSelector.setDisable(false);
        themeSelector.setDisable(false);
    }

    private void applyTheme(String theme) {
        // Remove all previous theme classes
        borderPane.getStyleClass().removeAll("default-theme", "dark-theme", "light-theme", "gradient-theme");

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
            case "Gradient":
                borderPane.getStyleClass().add("gradient-theme");
                break;
        }
    }
    private int getAIMove(List<Integer> availableCells) {
        String difficulty = difficultSelector.getValue();
        return switch (difficulty) {
            case "Hard" -> hardAI(availableCells);
            case "Medium" -> mediumAI(availableCells);
            default -> // Easy
                    availableCells.get((int) (Math.random() * availableCells.size()));
        };
    }
    //Feedback section
    private void openFeedbackDialog() {
        Stage feedbackStage = new Stage();
        feedbackStage.setTitle("Feedback");

        // Creating a label for the dialog
        Label feedbackLabel = new Label("We value your feedback! Please rate:");
        feedbackLabel.setWrapText(true);
        feedbackLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        // Smiley rating system using radio buttons (emoji faces)
        RadioButton happyFaceButton = new RadioButton("😀");
        RadioButton neutralFaceButton = new RadioButton("😐");
        RadioButton sadFaceButton = new RadioButton("😞");

        happyFaceButton.setStyle("-fx-font-size: 30px; -fx-text-fill: #4CAF50;");
        neutralFaceButton.setStyle("-fx-font-size: 30px; -fx-text-fill: #FF9800;");
        sadFaceButton.setStyle("-fx-font-size: 30px; -fx-text-fill: #F44336;");

        // Grouping the radio buttons in a ToggleGroup
        ToggleGroup smileyGroup = new ToggleGroup();
        happyFaceButton.setToggleGroup(smileyGroup);
        neutralFaceButton.setToggleGroup(smileyGroup);
        sadFaceButton.setToggleGroup(smileyGroup);

        // Set one of the smileys to be selected by default
        happyFaceButton.setSelected(true);

        HBox smileyBox = new HBox(20, happyFaceButton, neutralFaceButton, sadFaceButton);
        smileyBox.setAlignment(Pos.CENTER);

        // Text area for detailed feedback
        TextArea feedbackTextArea = new TextArea();
        feedbackTextArea.setPromptText("Type your feedback here...");
        feedbackTextArea.setPrefSize(400, 150);
        feedbackTextArea.setStyle("-fx-font-size: 14px; -fx-border-color: #4CAF50;");

        // Optionally, allow users to provide their email (for follow-ups)
        Label emailLabel = new Label("Your Email (Optional):");
        TextField emailField = new TextField();
        emailField.setPromptText("Your email address");

        // Submit and Cancel buttons
        Button submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        submitButton.setOnAction(event -> {
            String feedback = feedbackTextArea.getText().trim();
            String email = emailField.getText().trim();
            RadioButton selectedSmiley = (RadioButton) smileyGroup.getSelectedToggle();
            String rating = selectedSmiley != null ? selectedSmiley.getText() : "No Rating";
            if (!email.isEmpty() && !isValidEmail(email)) {
                showAlert("Invalid Email", "Please enter a valid email address.");
                return;
            }            saveFeedbackToFile(feedback, email, rating);
            feedbackStage.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        cancelButton.setOnAction(event -> feedbackStage.close());
        // Layout of the dialog
        VBox dialogVBox = new VBox(15, feedbackLabel, smileyBox, feedbackTextArea, emailLabel, emailField, submitButton, cancelButton);
        dialogVBox.setAlignment(Pos.CENTER);
        dialogVBox.setPadding(new Insets(20));

        // Create and set the scene for the feedback stage
        Scene feedbackScene = new Scene(dialogVBox, 450, 350);
        feedbackStage.setScene(feedbackScene);
        feedbackStage.show();
    }

    //Email validation
    private boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    // Save feedback to file (including smiley rating and optional email)
    private void saveFeedbackToFile(String feedback, String email, String rating) {
        if (feedback.isEmpty()) {
            showAlert("Invalid Feedback", "Feedback cannot be empty!");
            return;
        }
        try (FileWriter writer = new FileWriter("feedback.txt", true)) { // Append mode
            writer.write("Rating: " + rating + "\n");
            writer.write("Feedback: " + feedback + "\n");
            if (!email.isEmpty()) {
                writer.write("Email: " + email + "\n");
            }
            writer.write("---\n");
            showAlert("Thank You!", "Your feedback has been recorded.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save feedback.");
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}





/*
package org.example.tictactoefx;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

public class TicTacToeApp extends Application {
    private GameController gameController;
    private BoardView boardView;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        TopPanel topPanel = new TopPanel();
        boardView = new BoardView();
        boardView.addDelayAndUpdateStatus();


        gameController = new GameController(boardView);

        // Link GUI components with GameController
        topPanel.setGameController(gameController);
        boardView.setGameController(gameController);

        root.setTop(topPanel.getLayout());
        root.setCenter(boardView.getLayout());

        Scene scene = new Scene(root, 600, 600);

        // Apply initial theme
        scene.getStylesheets().add(getClass().getResource("/org/example/tictactoefx/style.css").toExternalForm());
        topPanel.setThemeChangeListener(theme -> {
            scene.getStylesheets().clear();

        });

        scene.setCursor(Cursor.HAND);
        stage.setTitle("Tic-Tac-Toe");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/tictactoefx/unnamed.png"))));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

 */





