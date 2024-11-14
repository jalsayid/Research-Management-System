package com.example.l;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ProjectPageAdmin implements Initializable {

    @FXML
    private TableColumn<Project, String> id;

    @FXML
    private TableColumn<Project, String> leader;

    @FXML
    private TableColumn<Project, String> name;
    @FXML
    private TableColumn<Project, String> machine;
    @FXML
    private TableView<Project> table;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField idInput;
    @FXML
    private TextField leaderInput;
    @FXML
    private Label wrongInput;
    ObservableList<Project> projects = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<>("ProjectName"));
        id.setCellValueFactory(new PropertyValueFactory<>("TeamID"));
        leader.setCellValueFactory(new PropertyValueFactory<>("Leader"));
        machine.setCellValueFactory(new PropertyValueFactory<>("MachineUsage"));
        setUpTable();
        table.setItems(projects); // Set the items for the TableView
    }
    @FXML
    void submit(ActionEvent event) {
        if(nameInput.getText().isEmpty() || idInput.getText().isEmpty() || leaderInput.getText().isEmpty()) {
            wrongInput.setText("Please enter all needed data!");
        }
        else {
            Project project = new Project(nameInput.getText(), idInput.getText(), leaderInput.getText(), 0);
            projects.add(project);
            table.setItems(projects);
            Project.addNewProject(project);
        }
    }
    @FXML
    void removeProject(ActionEvent event) {
        Project project = table.getSelectionModel().getSelectedItem();
        Project.remove(project);
        int selectedID = table.getSelectionModel().getSelectedIndex();
        table.getItems().remove(selectedID);
    }
    private void setUpTable() {
        try(Scanner read = new Scanner(new File("src/main/java/com/example/l/txt/Project.txt"))){
            read.nextLine();
            while (read.hasNext()) {
                String[] line = read.nextLine().split(", ");
                projects.add(new Project(line[0], line[1], line[2], Integer.parseInt(line[3])));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("file not found.");
        }
    }
}

