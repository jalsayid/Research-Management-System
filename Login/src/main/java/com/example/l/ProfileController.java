package com.example.l;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

// Java class representing the controller for handling the user profile view
public class ProfileController implements Initializable {

    // FXML elements injected from the corresponding FXML file
    @FXML
    private Label user_email;

    @FXML
    private Label user_name;

    @FXML
    private Label user_research_of_interest;

    @FXML
    private ListView<String> interestList;

    // File paths for Member and Admin data
    String MemberFileTxt=  "src/main/java/com/example/l/txt/Member.txt";
    String AdminFileTxt=  "src/main/java/com/example/l/txt/Admin.txt";

    // Initialize method called when the controller is initialized
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Read user data from file
        User user = readDataFromFile();

        // Set user information in the UI
        user_name.setText(user.getName());
        user_email.setText(user.getEmail());

        // Populate the research of interest list for students
        if (Login.EnteredUser().getId().charAt(0) == 's') {
            interestList.getItems().addAll(user.getResearchOfInterest().split(", "));
        } else {
            // Hide the research of interest label for admins
            user_research_of_interest.setText("");
        }
    }

    // Method to read user data from the file
    public User readDataFromFile() {
        User user = null;

        try {
            // Read data for students
            if (Login.EnteredUser().getId().charAt(0) == 's') {
                Scanner readMember = new Scanner(new File(MemberFileTxt));
                readMember.nextLine();
                String memberName = null;

                while (readMember.hasNext()) {
                    String[] memberLine = readMember.nextLine().split(", ");
                    String interest = "";

                    if (memberLine[1].equals(Login.EnteredUser().getId())) {
                        memberName = memberLine[0];
                        for (int i = 5; i < memberLine.length - 1; i++) {
                            interest += memberLine[i] + ", ";
                        }
                        user = new User(memberName, memberLine[1], memberLine[2], interest + memberLine[memberLine.length - 1]);
                        break; // Exit the loop since the user is found
                    }
                }
                readMember.close();
            } else {
                // Read data for admins
                Scanner readAdmin = new Scanner(new File(AdminFileTxt));
                readAdmin.nextLine();
                String name = null;

                while (readAdmin.hasNext()) {
                    String[] line = readAdmin.nextLine().split(", ");

                    if (line[1].equals(Login.EnteredUser().getId())) {
                        name = line[0];
                        user = new User(name, line[1], line[2]);
                        break; // Exit the loop since the user is found
                    }
                }
                readAdmin.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }

        return user;
    }
}