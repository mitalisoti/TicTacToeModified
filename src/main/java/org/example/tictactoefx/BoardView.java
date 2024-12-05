/*package org.example.tictactoefx;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.List;
import java.util.Objects;

public class BoardView {
    private final GridPane gridPane;
    private final Button[][] buttons = new Button[3][3];
    private final Label statusLabel;
    private GameController gameController;
    private final Image xImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/tictactoefx/x.png")));
    private final Image oImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/tictactoefx/o.png")));
    private final Button restartButton;  // Restart button

    public BoardView() {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Status label for user feedback
        statusLabel = new Label("Welcome to Tic Tac Toe!");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");
        statusLabel.setAlignment(Pos.CENTER);
        addDelayAndUpdateStatus();

        // Initialize grid buttons
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button();
                button.setPrefSize(120, 120);
                button.setStyle("-fx-font-size: 24;");
                final int r = row, c = col;

                int finalCol = col;
                int finalRow = row;
                button.setOnAction(e -> {
                    if (gameController != null) {
                        gameController.handleCellClick(finalRow * 3 + finalCol);
                    }
                });

                buttons[row][col] = button;
                gridPane.add(button, col, row);
            }
        }

        // Create the restart button and add functionality for resetting the game
        restartButton = new Button("Restart");
        restartButton.setStyle("-fx-font-size: 16px; -fx-background-color: lightblue;");
        restartButton.setOnAction(event -> {
            if (gameController != null) {
                gameController.resetGame();  // Reset the game when restart button is clicked
            }
        });
       // restartButton.setOnAction(e -> resetBoard());  // Reset the board when clicked
    }

    public void addDelayAndUpdateStatus() {
        // Create a PauseTransition to wait 1 second before updating the status
        PauseTransition delay = new PauseTransition(Duration.seconds(2)); // 1 second delay
        delay.setOnFinished(event -> {
            // Update the label text after the delay
            statusLabel.setText("Player X's Turn!");
        });
        delay.play(); // Start the delay
    }
    public void highlightWinningCells(List<int[]> winningCells) {
        for (int[] cell : winningCells) {
            int row = cell[0];
            int col = cell[1];
            // Implement the highlighting logic, such as changing the color or adding a border
            // Example: Change button color to highlight
            buttons[row][col].setStyle("-fx-background-color: yellow;");
        }
    }


    public VBox getLayout() {
        // Create a VBox layout with the grid, status label, and the restart button at the bottom
        VBox layout = new VBox(10, gridPane, statusLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefHeight(400);

        // Add the restart button at the bottom of the VBox
        VBox.setMargin(restartButton, new javafx.geometry.Insets(10));  // Add some spacing for visual appeal
        layout.getChildren().add(restartButton);  // Add the restart button after the grid and status label

        return layout;
    }

    public void updateCell(int row, int col) {
        Button button = buttons[row][col]; // Get the button at the specified position

        // Create an ImageView to display the image
        ImageView imageView = new ImageView();

        // Check if the current move is by Player X (human player)
        if (gameController.getCurrentPlayer() == GameController.PLAYER_ONE) {
            imageView.setImage(xImage);  // Set the X image for Player X
        } else {
            // For Player O (AI), set the O image
            imageView.setImage(oImage);  // Set the O image for AI (Player O)
        }

        // Set image fit size
        imageView.setFitWidth(80);  // Adjust the size of the image if necessary
        imageView.setFitHeight(80);

        // Set the ImageView as the graphic of the button
        button.setGraphic(imageView);

        // Optionally, disable the button to prevent further clicks after it's already clicked
        button.setDisable(true);  // Disable the button after it is clicked to avoid further clicks
    }



    public void resetBoard() {
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setText("");
                button.setGraphic(null);
                button.setDisable(false);  // Enable buttons after reset
            }
        }
        statusLabel.setText("Game Reset! Player X's turn.");
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void showMessage(String message) {
        statusLabel.setText(message);
    }
}

 */
