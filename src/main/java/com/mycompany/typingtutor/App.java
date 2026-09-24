package com.mycompany.typingtutor;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        
        int col = 0;
        int row = 0;
        
        // Add all the letters of the alphabet
        for (int i = 0; i < 26; i++) {
            Button button = new Button("" + (char) (i + 97));
            button.setMinSize(40, 40);
            
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
        button.setMinSize(40, 40);

        gridPane.add(button, 4, 2);
        
        // TextFields
        TextField displayTextField = new TextField("Text you should type");
        TextField typedTextField = new TextField("Type text here!");
        
//        typedTextField.setMinHeight(100);
        VBox fields = new VBox();
        fields.getChildren().addAll(displayTextField, typedTextField);
        
        
        // Vbox to store everything
        VBox app = new VBox();
        
        app.getChildren().addAll(fields, gridPane);
        
        var scene = new Scene(new StackPane(app), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}