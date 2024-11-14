package com.example.l;

import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//login
// Java class for handling the login functionality
public class Login {
    // FXML elements
    @FXML
    private Button button;

    @FXML
    private TextField userId;

    @FXML
    private PasswordField password;

    @FXML
    private Label wrongLogin;

    // Static User instance to store the logged-in user
    private static User user;

    // File path for the authorization file
    String AuthorizationFlie=  "src/main/java/com/example/l/txt/AuthorizationFlie.txt";

    // Event handler for the login button
    public void userLogin(ActionEvent event) throws IOException {
        // Perform login check
        checkLogin();
    }

    // Method to check the login credentials
    private void checkLogin() throws IOException {
        // Create an instance of the App class
        App app = new App();

        // Create a User instance with the entered user ID
        user = new User(userId.getText());

        // Check if the entered ID and password are correct
        if (correctIDAndPass(userId.getText(), password.getText())) {
            // If the user is an admin, change the scene to the admin view; otherwise, change to the user view
            if (isAdmin()) {
                app.changeScene("hello-view.fxml");
            } else {
                app.changeScene("hello-view_user.fxml");
            }
        } else if (userId.getText().isEmpty() || password.getText().isEmpty()) {
            // Display a message if the user fails to enter all required data
            wrongLogin.setText("Please enter all your data!");
        } else {
            // Display a message for wrong user ID or password
            wrongLogin.setText("Wrong userID or password!");
        }
    }

    // Static method to retrieve the logged-in user
    public static User EnteredUser() {
        return user;
    }

    // Method to check if the entered ID and password match any in the authorization file
    private boolean correctIDAndPass(String id, String pass) {
        try (Scanner read = new Scanner(new File(AuthorizationFlie))) {
            while (read.hasNext()) {
                // Check if the entered ID and password match any in the file
                if (read.next().replace(",", "").equals(id) && read.next().equals(pass)) {
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            // Handle file not found exception
            System.out.println("File not found.");
        }
        return false;
    }

    // Method to check if the logged-in user is an admin
    private boolean isAdmin() {
        // Check if the first character of the user ID is 'g'
        return userId.getText().charAt(0) == 'g';
    }
}
