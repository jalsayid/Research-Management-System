package com.example.l;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.fxml.Initializable;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;


// Define a class named AddTeamController that implements the Initializable interface
public class AddTeamController implements Initializable {
    // Declare a variable named MemberFileTxt and assign it the value "src/main/java/com/example/l/txt/Member.txt"
    String MemberFileTxt = "src/main/java/com/example/l/txt/Member.txt";

    // Declare a private Button named add_member_button
    @FXML
    private Button add_member_button;

    // Declare a private Pane named new_Reserve
    @FXML
    private Pane new_Reserve;

    // Declare a private Button named save_button
    @FXML
    private Button save_button;

    // Declare a private MenuButton named team_leader
    @FXML
    private MenuButton team_leader;

    // Declare a private ListView<MenuButton> named listView
    @FXML
    private ListView<MenuButton> listView;

    // Create an ObservableList<String> named members using the FXCollections.observableArrayList() method
    ObservableList<String> members = FXCollections.observableArrayList();

    // Override the initialize method from the Initializable interface
    //@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Call the loadUsers method
        loadUsers();

        // Set an action for the save_button
        save_button.setOnAction(event -> {
            save();
        });

        // Iterate over the members list and create MenuItems for each member
        for (String leaderName : members) {
            MenuItem menuItem = new MenuItem(leaderName);
            menuItem.setOnAction(event -> team_leader.setText(leaderName)); // Update label text
            team_leader.getItems().add(menuItem);
        }

        // Set an action for the add_member_button
        add_member_button.setOnAction((event) -> {
            MenuButton memberMenuButton1 = new MenuButton("Select Member");

            // Iterate over the members list and create MenuItems for each member
            for (String memberName : members) {
                MenuItem menuItem = new MenuItem(memberName);
                menuItem.setOnAction(event1 -> memberMenuButton1.setText(memberName)); // Update label text
                memberMenuButton1.getItems().add(menuItem);
            }
            listView.getItems().add(memberMenuButton1);
        });
    }

    // Define a private method named loadUsers
    private void loadUsers() {
        try (Scanner reader = new Scanner(new FileReader(MemberFileTxt))) {
            // Read and ignore the first line
            reader.nextLine();

            // Iterate over the remaining lines and add the first element to the members list
            while (reader.hasNext()) {
                String[] line = reader.nextLine().split(", ");
                members.add(line[0]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found.");
        }
    }

    // Define a private method named showAlert that displays an alert dialog
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Define a public method named save
    public void save() {
        // Create a new Random object named random
        Random random = new Random();

        // Check if team_leader is null or listView is empty
        if (team_leader == null || listView.getItems().isEmpty()) {
            // Show an alert with a validation error message
            showAlert("Validation Error", "Please fill in all required fields (Team Name, Leader, Members).");
            return; // Stop further processing
        } else {
            // Create an ArrayList<String> named memberNames
            ArrayList<String> memberNames = new ArrayList<>();

            // Iterate over the items in listView and add their text to memberNames
            for (MenuButton b : listView.getItems()) {
                memberNames.add(b.getText());
            }

            // Join the memberNames with a comma and space separator
            String membersString = String.join(", ", memberNames);

            // Create a new Team object with a generated team ID, null values for other attributes, and the selected leader and members
            Team team = new Team("A" + String.valueOf(random.nextInt(900) + 100), null, null, team_leader.getText(), membersString);

            // Add the new team to the Team class (assuming the addNewTeam method exists)
            Team.addNewTeam(team);

            // Show an alert indicating that the changes are saved successfully
            showAlert("Add New Team", "Your changes are saved successfully!");
        }
    }

    // Define a method named goBack that handles the goBack action
    @FXML
    void goBack(ActionEvent event) throws IOException {
        // Create a new instance of the App class
        App app = new App();

        // Call the changeScene method of the app instance to switch to the hello-view.fxml scene
        app.changeScene("hello-view.fxml");
    }
}