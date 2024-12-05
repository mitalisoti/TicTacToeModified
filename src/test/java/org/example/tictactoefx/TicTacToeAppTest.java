package org.example.tictactoefx;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeAppTest extends ApplicationTest {

    private TicTacToeApp app;

    @Override
    public void start(Stage stage) throws Exception {
        // Initialize the app and start it within the correct JavaFX thread
        app = new TicTacToeApp();
        app.start(stage);  // This will launch the stage properly on the FX thread
    }

    @Test
    void testInitialGameState() {
        // Test that all buttons are empty at the start
        for (Button button : app.btns) {
            assertNull(button.getGraphic(), "Button should be empty at the start");
        }

        // Ensure that the active player label shows "Your Turn" for Player 1
        assertEquals("Your Turn", app.activePlayerLabel.getText(), "Active player label should say 'Your Turn'");

        // Ensure that the restart button is disabled initially
        assertTrue(app.restartButton.isDisabled(), "Restart button should be disabled initially");
    }

    @Test
    void testPlayerMove() {
        // Simulate a move by Player 1 (clicking button at index 0)
        Button button = app.btns[0];
        clickOn(button);  // Simulate a click on the button at index 0

        // Verify that the button is updated with the "O" image for Player 1
        assertNotNull(button.getGraphic(), "Button should show 'O' image after Player 1's move");

        // Verify that the game state has been updated (position 0 should be Player 1's mark)
        assertEquals(TicTacToeApp.PLAYER_ONE, app.gameState[0], "Game state should reflect Player 1's move at index 0");

        // Verify that the active player has switched to Single player mode (Player 1 should not be active)
        assertEquals("AI's Turn", app.activePlayerLabel.getText(), "Active player label should show AI's turn after Player 1's move");
    }
}
