/*package org.example.tictactoefx;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class OpenFeedbackDialog {

    public void showFeedbackDialog() {
        Stage feedbackStage = new Stage();
        feedbackStage.setTitle("Feedback");

        Label feedbackLabel = new Label("We value your feedback! Please let us know your thoughts:");
        feedbackLabel.setWrapText(true);

        TextArea feedbackTextArea = new TextArea();
        feedbackTextArea.setPromptText("Type your feedback here...");
        feedbackTextArea.setPrefSize(400, 200);

        Button submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        submitButton.setOnAction(event -> {
            saveFeedbackToFile(feedbackTextArea.getText());
            feedbackStage.close();
        });

        VBox dialogVBox = new VBox(10, feedbackLabel, feedbackTextArea, submitButton);
        dialogVBox.setPadding(new Insets(20));
        feedbackStage.setScene(new Scene(dialogVBox));
        feedbackStage.show(); // Add this to display the feedback window



    }

    private void saveFeedbackToFile(String feedback) {
        if (feedback == null || feedback.trim().isEmpty()) {
            showAlert("Invalid Feedback", "Feedback cannot be empty!");
            return;
        }

        try (FileWriter writer = new FileWriter("feedback.txt", true)) { // Append mode
            writer.write("Feedback:\n");
            writer.write(feedback.trim());
            writer.write("\n---\n");
            showAlert("Thank You!", "Your feedback has been recorded.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save feedback.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.show();
    }
}

 */
