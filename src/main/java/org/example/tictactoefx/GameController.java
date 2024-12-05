/*package org.example.tictactoefx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameController {
    public static final char PLAYER_ONE = 'X';
    public static final char PLAYER_TWO = 'O';
    private static final char EMPTY = '\0';

    private final BoardView boardView;
    private final char[][] board;
    private final int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6}             // Diagonals
    };
    // Add this declaration in the GameController class
    private List<int[]> winningCells = new ArrayList<>();


    private char currentPlayer;
    private boolean gameOver;

    private String mode = "Single Player";
    private String difficulty = "Easy";

    public GameController(BoardView boardView) {
        this.boardView = boardView;
        this.board = new char[3][3];
        resetGame();
    }

    public void resetGame() {
        for (int i = 0; i < 3; i++) {
            Arrays.fill(board[i], EMPTY);
        }
        currentPlayer = PLAYER_ONE;  // Start with Player X
        gameOver = false;
        boardView.resetBoard();
        boardView.showMessage("Player X's turn");
    }

    public void handleMove(int cell) {
        if (gameOver) return;

        int row = cell / 3;
        int col = cell % 3;

        if (board[row][col] != EMPTY) return;

        board[row][col] = currentPlayer;
        boardView.updateCell(row, col);  // Pass row and col instead of cell index

        if (checkWin(row, col)) {
            gameOver = true;
            boardView.showMessage("Player " + currentPlayer + " wins!");
        } else if (isBoardFull()) {
            gameOver = true;
            boardView.showMessage("It's a tie!");
        } else {
            switchPlayer();
            if (mode.equals("Single Player") && currentPlayer == PLAYER_TWO) {
                makeAIMove(); // AI makes its move
            }
        }
    }


    // Add a getter method for currentPlayer
    public char getCurrentPlayer() {
        return currentPlayer;
    }

    private boolean checkWin(int row, int col) {
        return checkRow(row) || checkColumn(col) || checkDiagonals();
    }

    private boolean checkRow(int row) {
        return board[row][0] == currentPlayer && board[row][1] == currentPlayer && board[row][2] == currentPlayer;
    }

    private boolean checkColumn(int col) {
        return board[0][col] == currentPlayer && board[1][col] == currentPlayer && board[2][col] == currentPlayer;
    }

    private boolean checkDiagonals() {
        return (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
                (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) return false;
            }
        }
        return true;
    }

    private void makeAIMove() {
        List<Integer> availableCells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            int row = i / 3, col = i % 3;
            if (board[row][col] == EMPTY) {
                availableCells.add(i);
            }
        }
        int move = getAIMove(availableCells);
        handleMove(move);
    }

    private int getAIMove(List<Integer> availableCells) {
        switch (difficulty) {
            case "Hard":
                return hardAI(availableCells);
            case "Medium":
                return mediumAI(availableCells);
            default: // Easy
                return availableCells.get((int) (Math.random() * availableCells.size()));
        }
    }

    private int hardAI(List<Integer> availableCells) {
        for (int index : availableCells) {
            if (canWin(PLAYER_TWO, index)) return index; // Win
        }
        for (int index : availableCells) {
            if (canWin(PLAYER_ONE, index)) return index; // Block
        }
        if (availableCells.contains(4)) return 4; // Center
        List<Integer> corners = Arrays.asList(0, 2, 6, 8);
        for (int corner : corners) {
            if (availableCells.contains(corner)) return corner; // Corners
        }
        return availableCells.get((int) (Math.random() * availableCells.size())); // Random
    }

    private int mediumAI(List<Integer> availableCells) {
        if (Math.random() < 0.5) {
            return hardAI(availableCells);
        } else {
            return availableCells.get((int) (Math.random() * availableCells.size()));
        }
    }

    private boolean canWin(char player, int cell) {
        int row = cell / 3, col = cell % 3;
        board[row][col] = player;
        boolean win = checkWin(row, col);
        board[row][col] = EMPTY;
        return win;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == PLAYER_ONE) ? PLAYER_TWO : PLAYER_ONE;
        boardView.showMessage("Player " + currentPlayer + "'s turn");
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void handleCellClick(int row) {
        int col = 0;
        if (board[row][col] != EMPTY) {
            return; // Cell already occupied
        }

        // Mark the cell with the current player's symbol
        board[row][col] = currentPlayer;

        // Check if there's a winner
        if (checkForWin()) {
            // Pass the winning cells to BoardView to highlight them
            boardView.highlightWinningCells(winningCells); // Highlight the winning cells
            boardView.showMessage("Player " + currentPlayer + " Wins!");  // Display the win message
        } else {
            // Change turn to the other player
            switchPlayer();
            boardView.updateCell(row, col); // Update the button with the current player's symbol

            // If it's Single Player and AI's turn (PLAYER_TWO), make the AI move
            if (mode.equals("Single Player") && currentPlayer == PLAYER_TWO) {
                makeAIMove();
            }
        }
    }


    public boolean checkForWin() {
        // Check rows and columns for a win
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != EMPTY) {
                winningCells.clear();
                winningCells.add(new int[]{i, 0});
                winningCells.add(new int[]{i, 1});
                winningCells.add(new int[]{i, 2});
                return true; // Player wins on this row
            }
            // Check columns
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != EMPTY) {
                winningCells.clear();
                winningCells.add(new int[]{0, i});
                winningCells.add(new int[]{1, i});
                winningCells.add(new int[]{2, i});
                return true; // Player wins on this column
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != EMPTY) {
            winningCells.clear();
            winningCells.add(new int[]{0, 0});
            winningCells.add(new int[]{1, 1});
            winningCells.add(new int[]{2, 2});
            return true; // Player wins on the main diagonal
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != EMPTY) {
            winningCells.clear();
            winningCells.add(new int[]{0, 2});
            winningCells.add(new int[]{1, 1});
            winningCells.add(new int[]{2, 0});
            return true; // Player wins on the anti-diagonal
        }

        return false;
    }
}


*/