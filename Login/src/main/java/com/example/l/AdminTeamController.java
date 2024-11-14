package com.example.l;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

// Define a class named AdminTeamController that implements the Initializable interface
public class AdminTeamController implements Initializable {

    // Declare a private Button named add
    @FXML
    private Button add;

    // Declare a private Button named remove
    @FXML
    private Button remove;

    // Declare a private TableView<Team> named teamTableAdmin
    @FXML
    private TableView<Team> teamTableAdmin;

    // Declare a private TableColumn<Team, String> named idColumnAdmin
    @FXML
    private TableColumn<Team, String> idColumnAdmin;

    // Declare a private TableColumn<Team, String> named leaderColumnAdmin
    @FXML
    private TableColumn<Team, String> leaderColumnAdmin;

    // Declare a private TableColumn<Team, String> named machineAdmin
    @FXML
    TableColumn<Team, String> machineAdmin;

    // Declare a private TableColumn<Team, String> named membersColumnAdmin
    @FXML
    private TableColumn<Team, String> membersColumnAdmin;

    // Declare a private TableColumn<Team, String> named projectColumnAdmin
    @FXML
    private TableColumn<Team, String> projectColumnAdmin;

    // Create an ObservableList<Team> named teamsAdmin using the FXCollections.observableArrayList() method
    ObservableList<Team> teamsAdmin = FXCollections.observableArrayList();

    // Override the initialize method from the Initializable interface
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the cell value factories for the table columns
        idColumnAdmin.setCellValueFactory(new PropertyValueFactory<>("teamID"));
        machineAdmin.setCellValueFactory(new PropertyValueFactory<>("Machine"));
        projectColumnAdmin.setCellValueFactory(new PropertyValueFactory<>("project"));
        leaderColumnAdmin.setCellValueFactory(new PropertyValueFactory<>("leader"));
        membersColumnAdmin.setCellValueFactory(new PropertyValueFactory<>("members"));

        // Call the readDataFromFile method
        readDataFromFile();

        // Set the items of the teamTableAdmin TableView to the teamsAdmin list
        teamTableAdmin.setItems(teamsAdmin);
    }

    // Define a method named readDataFromFile that reads data from a file and populates the teamsAdmin list
    public void readDataFromFile() {
        try (Scanner readTeam = new Scanner(new FileReader("src/main/java/com/example/l/txt/TeamsFile.txt"))) {
            // Read and ignore the first line
            readTeam.nextLine();

            // Iterate over the remaining lines and create Team objects from the data
            while (readTeam.hasNext()) {
                String[] line = readTeam.nextLine().split(", ");
                Team team = new Team(line[0], Team.getTeamMachines(line[0]).toString().replace("[", "").replace("]", ""), line[1], line[3], Team.getTeamMembers(line[0]).toString().replace("[", "").replace("]", ""));
                teamsAdmin.add(team);
            }

        } catch (FileNotFoundException e) {
            System.out.println("file not found.");
        }
    }

    // Define a method named changeToAdd that handles the changeToAdd action
    @FXML
    void changeToAdd(ActionEvent event) throws IOException {
        // Create a new instance of the App class
        App app = new App();

        // Call the changeScene method of the app instance to switch to the add-team.fxml scene
        app.changeScene("add-team.fxml");
    }

    // Define a method named removeTeam that handles the removeTeam action
    @FXML
    void removeTeam(ActionEvent event) {
        // Get the selected team from the teamTableAdmin TableView
        Team team = teamTableAdmin.getSelectionModel().getSelectedItem();

        // Remove the team from the Team class (assuming the remove method exists)
        Team.remove(team);

        // Get the index of the selected team
        int selectedID = teamTableAdmin.getSelectionModel().getSelectedIndex();

        // Remove the selected team from the teamTableAdmin TableView
        teamTableAdmin.getItems().remove(selectedID);
    }
}