package com.mycompany.typingtutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    List<KeyCode> supportedKeys = new ArrayList<>(Arrays.asList(
            KeyCode.A,
            KeyCode.B,
            KeyCode.C,
            KeyCode.D,
            KeyCode.E,
            KeyCode.F,
            KeyCode.G,
            KeyCode.H,
            KeyCode.I,
            KeyCode.J,
            KeyCode.K,
            KeyCode.L,
            KeyCode.M,
            KeyCode.N,
            KeyCode.O,
            KeyCode.P,
            KeyCode.Q,
            KeyCode.R,
            KeyCode.S,
            KeyCode.T,
            KeyCode.U,
            KeyCode.V,
            KeyCode.W,
            KeyCode.X,
            KeyCode.Y,
            KeyCode.Z,
            KeyCode.SHIFT,
            KeyCode.SPACE
            ));
    
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
        
        // TextFields
        TextField displayTextField = new TextField("Text you should type");
        TextField typedTextField = new TextField();
        typedTextField.setPromptText("Type text here!");

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
        
        
        // Add logic for buttons and change style
        for (Node node: gridPane.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).setOnAction(btn -> {
                    Button source = (Button) btn.getSource();
                    typedTextField.setText(typedTextField.getText() + source.getUserData());
                });
            }
            
            node.setStyle("-fx-background-color:#ececec; -fx-border-color:#000000");
        }
        
        // Label to show what the user pressed
        Label displayLabel = new Label("Nothing has been pressed yet");
        displayLabel.setMinSize(100, 40);
        displayLabel.setStyle("-fx-background-color:#ececec; -fx-border-color:#000000; -fx-font-size: 12px");
        displayLabel.setPadding(new Insets(0, 0, 0, 5));
        VBox.setMargin(displayLabel, new Insets(10, 10, 10, 10));
        
        // Vbox to store everything
        VBox app = new VBox();
        
        app.getChildren().addAll(fields, gridPane, displayLabel);
        
        var scene = new Scene(new StackPane(app), 640, 480);
        
        // Physical keyboard logic
        // Changes button style when clicked
        // TODO: make the button also change style when theyre clicked manually
        scene.setOnKeyPressed(event -> {
            gridPane.getChildren().stream().forEach(action -> action.setStyle("-fx-background-color:#ececec; -fx-border-color:#000000"));
            
            String character = event.getText();
            KeyCode keyCode = event.getCode();
            
            // Add key to the textField
            typedTextField.setText(typedTextField.getText() + character);
            
            // Update the display label
            if (!supportedKeys.contains(keyCode)) {
                displayLabel.setText("Not handled: " + keyCode.toString());
                displayLabel.setStyle("-fx-background-color:#ececec; -fx-border-color:#000000; -fx-font-size: 12px; -fx-text-fill: #ff2020");
            } else {
                displayLabel.setText("Pressed key: " + character);
                displayLabel.setStyle("-fx-background-color:#ececec; -fx-border-color:#000000; -fx-font-size: 12px; -fx-text-fill: #000000");
            }
                 
            
            // Change appearamce of correponding virtual key
            for (Node node : gridPane.getChildren()) {
                if (node.getUserData().equals(character)) {
                    node.setStyle("-fx-background-color:#bcbcbc; -fx-border-color:#000000");
                }
            }
        });
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}