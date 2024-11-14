package com.example.l;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Stage page;

    @Override
    public void start(Stage stage) throws IOException {
        page = stage;
        // Load the Login.fxml file and create a parent node
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        // Set the scene for the stage
        stage.setScene(scene);
        // Disable window resizing
        stage.setResizable(false);
        // Show the stage
        stage.show();
    }

    // Method to change the scene based on the provided FXML file
    public void changeScene(String fxml) throws IOException {
        // Load the FXML file and create a parent node
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        // Set the root node of the current scene to the new parent node
        page.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}


