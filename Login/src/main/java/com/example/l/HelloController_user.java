package com.example.l;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import java.io.*;
import java.util.*;

// Define file paths for various text files
public class HelloController_user implements Initializable {
    String MachineNamesFileTxt=  "src/main/java/com/example/l/txt/MachinesNamesFile.txt";
    String ProjectFileTxt=  "src/main/java/com/example/l/txt/Project.txt";
    String TeamsFileTxt=  "src/main/java/com/example/l/txt/TeamsFile.txt";
    String MemberFileTxt=  "src/main/java/com/example/l/txt/Member.txt";

    // FXML elements
    @FXML
    private Button addReservationButton;

    @FXML
    private Button editDeleteButton;

    @FXML
    private Pane Reserve_control;

    @FXML
    private Button viesReservationButton;

    public String memberName; // Member name variable

    @FXML
    private Label Projects_num = null;

    @FXML
    private Label Teams_num = null;

    @FXML
    private VBox pnitems = null;

    // Initialize method
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        // Load machine viewer and reservation pane
        machineLoad("MachineUserViewer.fxml");
        loadReservationPane("New_Reservation.fxml");

        // Find member machines and update labels
        List<String> memberMachines = findMemberMachines(memberName);
        try {
            File file = new File(MemberFileTxt);
            Scanner scanner = new Scanner(file);

            // Read Member.txt file and update labels based on member data
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] array = line.split(", ");
                if (array[1].trim().equals(Login.EnteredUser().getId())) {
                    Projects_num.setText(array[3].trim());
                    Teams_num.setText(array[4].trim());
                    break;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Load machine viewer using the provided FXML file name
    private void machineLoad(String fxmlFileName) {
        pnitems.getChildren().clear();
        try {
            Node node = FXMLLoader.load(getClass().getResource(fxmlFileName));
            pnitems.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Find machines associated with a member in the TeamsFile.txt and Project.txt files
    public static List<String> findMemberMachines(String memberName) {
        List<String> memberProjects = new ArrayList<>();
        List<String> memberMachines = new ArrayList<>();

        // Read TeamsFile.txt to find projects associated with the member
        try (Scanner teamsScanner = new Scanner(new File("src/main/java/com/example/l/txt/TeamsFile.txt"))) {
            while (teamsScanner.hasNextLine()) {
                String line = teamsScanner.nextLine();
                String[] teamData = line.split(", ");

                // Check if memberName is present in the team
                if (containsMember(teamData, memberName)) {
                    memberProjects.add(teamData[1]); // Add the project name to memberProjects
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Read Project.txt to find machines associated with the member's projects
        try (Scanner projectScanner = new Scanner(new File("src/main/java/com/example/l/txt/Project.txt"))) {
            while (projectScanner.hasNextLine()) {
                String line = projectScanner.nextLine();
                String[] projectData = line.split(", ");

                // Check if the project is in memberProjects
                if (memberProjects.contains(projectData[0])) {
                    int machinesNum = Integer.parseInt(projectData[3].trim());
                    for (int i = 4; i < machinesNum + 4; i++) {
                        memberMachines.add(projectData[i]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return memberMachines;
    }

    // Check if a member is present in the team
    private static boolean containsMember(String[] teamData, String memberName) {
        for (int i = 3; i < teamData.length; i++) {
            if (teamData[i].equals(memberName)) {
                return true;
            }
        }
        return false;
    }

    // Event handler for adding a reservation
    @FXML
    void addReservation(ActionEvent event) {
        loadReservationPane("New_Reservation.fxml");
    }

    // Event handler for editing a reservation
    @FXML
    void editReservation(ActionEvent event) {
        loadReservationPane("Edit_Reservation.fxml");
    }

    // Event handler for viewing a reservation
    @FXML
    void viewReservation(ActionEvent event) {
        loadReservationPane("viewReservaationMember.fxml");
    }

    // Load reservation pane using the provided FXML file name
    private void loadReservationPane(String fxmlFileName) {
        Reserve_control.getChildren().clear();
        try {
            Node node = FXMLLoader.load(getClass().getResource(fxmlFileName));
            Reserve_control.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
