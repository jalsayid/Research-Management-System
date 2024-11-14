package com.example.l;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;


// Java class for handling the Machine User Viewer functionality
public class MachineUserViewer implements Initializable {

    // File path for machine data
    String MachineFileNameTxt = "src/main/java/com/example/l/txt/MachinesNamesFile.txt";

    // FXML elements
    @FXML
    private TextField Machine_Namee;

    @FXML
    private TextField Machine_Id1;

    @FXML
    private TextField Machine_Usage;

    @FXML
    private TableView<Machine> table;

    @FXML
    private TableColumn<Machine, String> Machine_Name;

    @FXML
    private TableColumn<Machine, String> Machine_Id;

    @FXML
    private TableColumn<Machine, String> Machine_intrest;

    // ObservableList to store Machine objects for the TableView
    ObservableList<Machine> Machines = FXCollections.observableArrayList();

    // Initialize method called when the controller is initialized
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set cell value factories for the TableView columns
        Machine_Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Machine_Id.setCellValueFactory(new PropertyValueFactory<>("MachineId"));
        Machine_intrest.setCellValueFactory(new PropertyValueFactory<>("Usage"));

        // Set up the table
        setUpTable();

        // Set the items in the table
        table.setItems(Machines);
    }

    // Method to set up the TableView with data from the file
    private void setUpTable() {
        try (Scanner read = new Scanner(new File(MachineFileNameTxt))) {
            read.nextLine();
            // Read data from the file and add machines to the ObservableList
            while (read.hasNext()) {
                String[] line = read.nextLine().split(", ");
                Machines.add(new Machine(line[0], line[1], line[2]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
}
