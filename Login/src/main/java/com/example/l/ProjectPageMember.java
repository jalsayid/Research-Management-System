package com.example.l;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ProjectPageMember implements Initializable {

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

    private void setUpTable() {
        try (Scanner readMember = new Scanner(new File("src/main/java/com/example/l/txt/Member.txt"));
             Scanner readProject = new Scanner(new File("src/main/java/com/example/l/txt/Project.txt"))) {
            readMember.nextLine();
            String memberName = null; // Declare memberName outside the loop

            while (readMember.hasNext()) {
                String[] memberLine = readMember.nextLine().split(", ");
                if (memberLine[1].equals(Login.EnteredUser().getId())) {
                    memberName = memberLine[0]; // Assign memberName value
                    break;
                }
            }

            while (readProject.hasNext()) {
                String[] lineP = readProject.nextLine().split(", ");
                if (memberName != null && Member.getTeams(memberName).contains(lineP[1])) {
                    projects.add(new Project(lineP[0], lineP[1], lineP[2], Integer.parseInt(lineP[3])));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
}


