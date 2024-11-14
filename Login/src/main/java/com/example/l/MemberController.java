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
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

// Java class representing the controller for handling the Member view
public class MemberController implements Initializable {

    // FXML elements injected from the corresponding FXML file
    @FXML
    private Button add;

    @FXML
    private TextField email;

    @FXML
    private TableColumn<Member, String> emailC;

    @FXML
    private TableColumn<Member, String> idC;

    @FXML
    private TextField name;

    @FXML
    private TableColumn<Member, String> nameC;

    @FXML
    private TableColumn<Member, Integer> projectsC;

    @FXML
    private Button remove;

    @FXML
    private TableColumn<Member, String> researchC;

    @FXML
    private TableView<Member> tableM;

    @FXML
    private Label wrongInput;

    @FXML
    private TableColumn<Member, Integer> teamsC;

    // ObservableList to hold Member objects for the TableView
    ObservableList<Member> members = FXCollections.observableArrayList();

    // Initialize method called when the controller is initialized
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set cell value factories for each column
        nameC.setCellValueFactory(new PropertyValueFactory<>("Name"));
        idC.setCellValueFactory(new PropertyValueFactory<>("Id"));
        emailC.setCellValueFactory(new PropertyValueFactory<>("Email"));
        projectsC.setCellValueFactory(new PropertyValueFactory<>("NumOfProjects"));
        teamsC.setCellValueFactory(new PropertyValueFactory<>("NumOfTeams"));
        researchC.setCellValueFactory(new PropertyValueFactory<>("ResearchOfInterest"));

        // Populate the TableView with data
        setUpTable();

        // Set the items for the TableView
        tableM.setItems(members);
    }

    // Method to populate the ObservableList with data from the file
    private void setUpTable() {
        try (Scanner read = new Scanner(new File("src/main/java/com/example/l/txt/Member.txt"))) {
            read.nextLine();
            while (read.hasNext()) {
                String[] line = read.nextLine().split(", ");
                String research = "";
                for (int i = 5; i < line.length - 1; i++) {
                    research += line[i] + ", ";
                }
                // Create Member objects and add them to the ObservableList
                members.add(new Member(line[0], line[1], line[2], Integer.parseInt(line[3]), Integer.parseInt(line[4]), research + line[line.length - 1]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found.");
        }
    }

    // Event handler for the "Add" button
    @FXML
    void addM(ActionEvent event) {
        Random random = new Random();
        if (name.getText().isEmpty() || email.getText().isEmpty()) {
            wrongInput.setText("Please enter all needed data!");
        } else {
            // Create a new Member object and add it to the ObservableList
            Member member = new Member(name.getText(), "s" + random.nextInt((900) + 100), email.getText(), 0, 0, "null");
            members.add(member);
            // Set the items for the TableView
            tableM.setItems(members);
            // Add the new member to the file
            Member.addNewMember(member);
        }
    }

    // Event handler for the "Remove" button
    @FXML
    void removeM(ActionEvent event) {
        // Get the selected member from the TableView
        Member member = tableM.getSelectionModel().getSelectedItem();
        // Remove the member from the file and the ObservableList
        Member.remove(member);
        int selectedID = tableM.getSelectionModel().getSelectedIndex();
        tableM.getItems().remove(selectedID);
    }

}
