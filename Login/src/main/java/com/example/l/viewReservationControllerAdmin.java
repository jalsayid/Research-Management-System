package com.example.l;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;


public class viewReservationControllerAdmin implements Initializable {

    @FXML
    private TableColumn<Reservation, String> reservationId;

    @FXML
    private TableColumn<Reservation, String> project;

    @FXML
    private TableColumn<Reservation, String> date;
    @FXML
    private TableColumn<Reservation, String> machine;
    @FXML
    private TableColumn<Reservation, String> team;
    @FXML
    private TableView<Reservation> table;
    ObservableList<Reservation> reservations = FXCollections.observableArrayList();
    String RecervationFile = "src/main/java/com/example/l/txt/RecervationFile.txt";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reservationId.setCellValueFactory(new PropertyValueFactory<>("reservationID"));
        project.setCellValueFactory(new PropertyValueFactory<>("project"));
        team.setCellValueFactory(new PropertyValueFactory<>("TeamId"));
        machine.setCellValueFactory(new PropertyValueFactory<>("machine"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        setUpTable();
        table.setItems(reservations); // Set the items for the TableView
    }
    @FXML
    void removeReservation(ActionEvent event) throws IOException {
        Reservation reservationSelected = table.getSelectionModel().getSelectedItem();
        Reservation reservation = new Reservation();
        reservation.deleteReservation(reservationSelected.getReservationID());
        int selectedID = table.getSelectionModel().getSelectedIndex();
        table.getItems().remove(selectedID);
    }
    private void setUpTable() {
        try(Scanner read = new Scanner(new File(RecervationFile))){
            read.nextLine();
            while (read.hasNext()) {
                String[] line = read.nextLine().split(", ");
                reservations.add(new Reservation(line[0], line[1], line[2], line[3], line[4]));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("file not found.");
        }
    }
}


