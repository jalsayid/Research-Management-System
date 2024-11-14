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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

// Java class representing the controller for handling the Member Team view
public class MemberTeamController implements Initializable {

    // File paths for Member and Teams data
    String MemberFileTxt = "src/main/java/com/example/l/txt/Member.txt";
    String TeamsFileTxt = "src/main/java/com/example/l/txt/TeamsFile.txt";

    // FXML elements injected from the corresponding FXML file
    @FXML
    private TableView<Team> teamTable;

    @FXML
    private TableColumn<Team, String> idColumn;

    @FXML
    private TableColumn<Team, String> machine;

    @FXML
    private TableColumn<Team, String> projectColumn;

    @FXML
    private TableColumn<Team, String> leaderColumn;

    @FXML
    private TableColumn<Team, String> membersColumn;

    // ObservableList to hold Team objects for the TableView
    ObservableList<Team> teams = FXCollections.observableArrayList();

    // Initialize method called when the controller is initialized
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set cell value factories for each column
        idColumn.setCellValueFactory(new PropertyValueFactory<>("teamID"));
        machine.setCellValueFactory(new PropertyValueFactory<>("machine"));
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("project"));
        leaderColumn.setCellValueFactory(new PropertyValueFactory<>("leader"));
        membersColumn.setCellValueFactory(new PropertyValueFactory<>("members"));

        // Read data from files and populate the TableView
        readDataFromFile();

        // Set the items for the TableView
        teamTable.setItems(teams);
    }

    // Method to read data from files and populate the ObservableList
    public void readDataFromFile() {
        try (Scanner readMember = new Scanner(new File(MemberFileTxt));
             Scanner readTeam = new Scanner(new FileReader(TeamsFileTxt))) {
            readTeam.nextLine();
            readMember.nextLine();
            String memberName = null; // Declare memberName outside the loop

            // Find the name of the current member
            while (readMember.hasNext()) {
                String[] memberLine = readMember.nextLine().split(", ");
                if (memberLine[1].equals(Login.EnteredUser().getId())) {
                    memberName = memberLine[0]; // Assign memberName value
                    break;
                }
            }

            // Read team data and add relevant teams to the ObservableList
            while (readTeam.hasNext()) {
                String[] line = readTeam.nextLine().split(", ");
                if (memberName != null && Member.getTeams(memberName).contains(line[0])) {
                    Team team = new Team(line[0], Team.getTeamMachines(line[0]).toString().replace("[", "").replace("]", ""), line[1], line[3], Team.getTeamMembers(line[0]).toString().replace("[", "").replace("]", ""));
                    teams.add(team);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("file not found.");
        }

    }

}
