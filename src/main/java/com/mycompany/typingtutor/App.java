package com.mycompany.typingtutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    List<KeyCode> supportedKeys = new ArrayList<>(Arrays.asList(
            KeyCode.A,KeyCode.B,KeyCode.C,KeyCode.D,KeyCode.E,
            KeyCode.F,KeyCode.G,KeyCode.H,KeyCode.I,KeyCode.J,
            KeyCode.K,KeyCode.L,KeyCode.M,KeyCode.N,KeyCode.O,
            KeyCode.P,KeyCode.Q,KeyCode.R,KeyCode.S,KeyCode.T,
            KeyCode.U,KeyCode.V,KeyCode.W,KeyCode.X,KeyCode.Y,
            KeyCode.Z,
            KeyCode.SHIFT,
            KeyCode.SPACE,
            KeyCode.BACK_SPACE,
            KeyCode.PERIOD
            ));
    
    List<String> sampleTexts = new ArrayList<>(Arrays.asList(
            "Try typing this text. Do it as quickly and accurately as you can.",
            "Next type another line of input data.",
            "The quick brown fox jumps over the lazy dog.",
            "Five big quacking zephyrs jolt my wax bed.",
            "Sympathizing would fix Quaker objectives.",
            "A large fawn jumped quickly over white zinc boxes."));
    
    int sampleTextIdx = 0;
    
    int correctStrokes = 0;
    int incorrectStrokes = 0;
    boolean isShiftPressed = false;
    
    @Override
    public void start(Stage stage) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        
        int col = 0;
        int row = 0;
        
        int btnSizeX = 50;
        int btnSizeY = 50;
        
        // Add all the letters of the alphabet
        for (int i = 0; i < 26; i++) {
            Button button = new Button("" + (char) (i + 97));
            button.setUserData("" + (char) (i + 97));
            button.setMinSize(btnSizeX, btnSizeY);
            
            gridPane.add(button, row, col);
            
            row++;
            
            if (row > 10) {
                row = 0;
                col++;
            }
        }
        
        // Add additional keys
        // TODO: add it in the for loop above
        Button button = new Button("Shift");
        button.setMinSize(btnSizeX, btnSizeY);
        button.setUserData("*to be implemented*");
        gridPane.add(button, 4, 2);
        
        Button shiftButton = new Button("Space");
        shiftButton.setMinSize(btnSizeX, btnSizeY);
        shiftButton.setUserData(" ");
        gridPane.add(shiftButton, 5, 2);
        
        Button periodButton = new Button(".");
        periodButton.setMinSize(btnSizeX, btnSizeY);
        periodButton.setUserData(".");
        gridPane.add(periodButton, 6, 2);
        
        
        // Apply style to all buttons
        gridPane.getChildren().stream().forEach(action -> action.setStyle("-fx-background-color:#ececec; -fx-border-color:#000000"));
        
        // TextFields
        TextField typedTextField = new TextField();
        TextField displayTextField = new TextField();
        typedTextField.setPromptText("Type text here!");
        displayTextField.setPromptText(sampleTexts.get(sampleTextIdx));
        typedTextField.setDisable(true);
        displayTextField.setDisable(true);
        typedTextField.setStyle("-fx-font-size: 12px; -fx-prompt-text-fill: #000000");
        displayTextField.setStyle("-fx-font-size: 12px; -fx-prompt-text-fill: #000000");

        VBox fields = new VBox();
        fields.getChildren().addAll(displayTextField, typedTextField);
        
        // Apply the same settings for both fields
        for (Node node: fields.getChildren()) {
            if (node instanceof TextField) {
                TextField field = (TextField) node;
                field.setEditable(false);
                field.setMinHeight(100);

                VBox.setMargin(field, new Insets(5, 10, 10, 10));
            }
        }
        
        // Label to show what the user pressed
        Label displayLabel = new Label("Nothing has been pressed yet");
        displayLabel.setMinSize(100, 40);
        displayLabel.setStyle("-fx-background-color:#ececec; -fx-border-color:#000000; -fx-font-size: 12px");
        displayLabel.setPadding(new Insets(0, 0, 0, 5));
        VBox.setMargin(displayLabel, new Insets(10, 10, 10, 10));
        
        // Button to let the user move to the next input
        Button nextBtn = new Button("Next");
        HBox.setMargin(nextBtn, new Insets(0, 0, 0, 10));
        
        Label textCounterLabel = new Label("1 of 6");
        
        Label statsLabel = new Label("Correct: 0 | Incorrect: 0");
        
        nextBtn.setOnAction(event -> {
            if (++sampleTextIdx < 6) {
                displayTextField.setText(sampleTexts.get(sampleTextIdx));
                textCounterLabel.setText("" + (sampleTextIdx + 1) + " of " + sampleTexts.size());
                typedTextField.clear();
            } 
        });
        
        Button resetBtn = new Button("Reset");
        VBox.setMargin(resetBtn, new Insets(0, 0, 0, 10));
        
        resetBtn.setOnAction(event -> {
            sampleTextIdx = 0;
            displayTextField.setText(sampleTexts.get(sampleTextIdx));
            textCounterLabel.setText("" + (sampleTextIdx + 1) + " of " + sampleTexts.size());
            typedTextField.clear();
        });
        
        
        HBox nextBtnHbox = new HBox(nextBtn, textCounterLabel);
        VBox buttonVbox = new VBox(nextBtnHbox, resetBtn);
        HBox infoHbox = new HBox(displayLabel, buttonVbox);
        infoHbox.setPadding(new Insets(10,10,10,10));
        
        // Vbox to store everything
        VBox app = new VBox();
        
        app.getChildren().addAll(fields, gridPane, infoHbox, statsLabel);
        
        var scene = new Scene(new StackPane(app), 640, 480);
        
        // Physical keyboard logic
        // Changes button style when clicked
        // TODO: make the button also change style when theyre clicked manually
        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            String character = isShiftPressed ? keyCode.getChar().toUpperCase() : keyCode.getChar().toLowerCase();
                
            String typedText = typedTextField.getText();
            
            if (keyCode.equals(KeyCode.SHIFT)) {
                isShiftPressed = true;
            }
            
            // Update the display label
            if (!supportedKeys.contains(keyCode)) {
                displayLabel.setText("Not handled: " + keyCode.getName());
                displayLabel.setStyle("-fx-background-color:#ececec; -fx-border-color:#000000; -fx-font-size: 12px; -fx-text-fill: #ff2020");
            } else {
                displayLabel.setStyle("-fx-background-color:#ececec; -fx-border-color:#000000; -fx-font-size: 12px; -fx-text-fill: #000000");
                displayLabel.setText("Pressed key: " + keyCode.getName());
                
                // Add key to the textField
                if (supportedKeys.contains(keyCode) && keyCode != KeyCode.SHIFT && keyCode != KeyCode.BACK_SPACE) {
                    typedTextField.setText(typedText + character);
                    
                    // Update typedText variable
                    typedText = typedTextField.getText();
                    
                } else if (keyCode == KeyCode.BACK_SPACE) {
                    // Removes last key and updates counters
                    if (!typedText.isEmpty()) {
                        int currentIdx = typedText.length() - 1;
                        char lastChar = typedText.charAt(currentIdx);
                        char expectedChar = sampleTexts.get(sampleTextIdx).charAt(currentIdx);

                        if (lastChar == expectedChar) {
                            if (correctStrokes > 0) {
                                correctStrokes--;
                            }
                        } else {
                            if (incorrectStrokes > 0) {
                                incorrectStrokes--;
                            }
                        }

                        typedTextField.setText(typedText.substring(0, currentIdx));

                        statsLabel.setText("Correct: " + correctStrokes + " | Incorrect: " + incorrectStrokes);
                    }
                } 
                
                // Updates counter labels
                if (!typedText.isEmpty() && keyCode != KeyCode.BACK_SPACE && keyCode != KeyCode.SHIFT) {
                    int currentIdx = typedText.length() - 1;
                    if (currentIdx < sampleTexts.get(sampleTextIdx).length()) {
                        char expected = sampleTexts.get(sampleTextIdx).charAt(currentIdx);
                        char actual = typedText.charAt(currentIdx);
                        
                        if (expected == actual) {
                            correctStrokes++;
                        } else {
                            incorrectStrokes++;
                        }
                        
                        statsLabel.setText("Correct: " + correctStrokes + " | Incorrect: " + incorrectStrokes);
                    }
                }
            }
            
            // Change appearamce of correponding virtual key
            for (Node node : gridPane.getChildren()) {
                if (node.getUserData().equals(character)) {
                    node.setStyle("-fx-background-color:#bcbcbc; -fx-border-color:#000000");
                }
            }
        });
        
        scene.setOnKeyReleased(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.SHIFT)) {
                isShiftPressed = false;
            }

            // Reset virtual key style on release
            String character = keyCode.getChar().toLowerCase();
            for (Node node : gridPane.getChildren()) {
                if (node.getUserData() != null && node.getUserData().toString().equalsIgnoreCase(character)) {
                    node.setStyle("-fx-background-color:#ececec; -fx-border-color:#000000");
                }
            }
        });
        
        stage.setScene(scene);
        stage.show();
        
        app.setFocusTraversable(true);
        app.requestFocus();
    }

    public static void main(String[] args) {
        launch();
    }

}