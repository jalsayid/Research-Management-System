package com.example.l
        ;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

// Java class for handling the Main Page functionality
public class MainPage implements Initializable {

    // File path for member data
    String MemberFileTxt = "src/main/java/com/example/l/txt/Member.txt";

    // FXML element
    @FXML
    private AnchorPane Main_page; // No need to initialize to null, FXML will inject it

    // Variable to store member name
    public String memberName;

    // Initialize method called when the controller is initialized
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Handle user with ID starting with "g"
        if (Login.EnteredUser().getId().startsWith("g")) {
            try {
                // Load the FXML for admin user
                Node nodes = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Main_page.getChildren().add(nodes); // Make sure Main_page is injected by FXML
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Handle user with ID starting with "s"
        else if (Login.EnteredUser().getId().startsWith("s")) { // Changed to else-if to avoid unnecessary checks
            try {
                // Reading the member information
                Scanner readMember = new Scanner(new File(MemberFileTxt));
                while (readMember.hasNext()) {
                    String[] memberLine = readMember.nextLine().split(", ");
                    if (memberLine[1].equals(Login.EnteredUser().getId())) {
                        memberName = memberLine[0];
                        break;
                    }
                }
                readMember.close(); // Close the scanner here to avoid resource leak

                // Load the FXML for user
                Node nodes = FXMLLoader.load(getClass().getResource("hello-view_user.fxml"));
                Main_page.getChildren().add(nodes);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
