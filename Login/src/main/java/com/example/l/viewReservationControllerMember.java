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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class viewReservationControllerMember implements Initializable {

    @FXML
    private TableColumn<Reservation, String> reservationId;

    @FXML
    private TableColumn<Reservation, String> project;

    @FXML
    private TableColumn<Reservation, String> date;
    @FXML
    private TableColumn<Reservation, String> team;
    @FXML
    private TableColumn<Reservation, String> machine;
    @FXML
    private TableView<Reservation> table;
    ObservableList<Reservation> reservations = FXCollections.observableArrayList();
    ArrayList<String> projects = new ArrayList<>();
    String ProjectFileTxt=  "src/main/java/com/example/l/txt/Project.txt";
    String MemberFileTxt=  "src/main/java/com/example/l/txt/Member.txt";
    String RecervationFileTxt= "src/main/java/com/example/l/txt/RecervationFile.txt";

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
        try (Scanner readMember = new Scanner(new File(MemberFileTxt));
             Scanner readProject = new Scanner(new File(ProjectFileTxt));
             Scanner readReservations = new Scanner(new File(RecervationFileTxt));) {
            readMember.nextLine();
            String memberName = null; // Declare memberName outside the loop

            while (readMember.hasNext()) {
                String[] memberLine = readMember.nextLine().split(", ");
                if (memberLine[1].equals(Login.EnteredUser().getId())) {
                    memberName = memberLine[0]; // Assign memberName value
                    break;
                }
            }
            readMember.close();

            while (readProject.hasNext()) {
                String[] lineP = readProject.nextLine().split(", ");
                System.out.println("Member Teams: " + Member.getTeams(memberName));
                if (memberName != null && Member.getTeams(memberName).contains(lineP[1])) {
                    projects.add(lineP[0]);
                }
            }

            readProject.close();

            while (readReservations.hasNext()){
                String[] lineR = readReservations.nextLine().split(", ");
                System.out.println("Reservation Team: " + lineR[1]);
                if (projects.contains(lineR[1])) {
                    reservations.add(new Reservation(lineR[0], lineR[1], lineR[2], lineR[3], lineR[4]));
                }
            }

            readReservations.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
}


