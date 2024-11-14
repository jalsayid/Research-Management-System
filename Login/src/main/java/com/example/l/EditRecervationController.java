package com.example.l;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class EditRecervationController implements Initializable {

    @FXML
    private DatePicker TimeDatePicker;

    @FXML
    private Button deleteButton;

    @FXML
    private ChoiceBox<String> interestsBox;

    @FXML
    private ChoiceBox<String> machineBox;
    @FXML
    private ChoiceBox<String> TeamsBox;

    @FXML
    private ChoiceBox<String> projectBox;

    @FXML
    private TextField reservationIdField;

    @FXML
    private Button saveButton;

    @FXML
    private Button suggestMachinesButton;
    String MachineFileTxt=  "src/main/java/com/example/l/txt/MachineFile.txt";
    String MemberFileTxt=  "src/main/java/com/example/l/txt/Member.txt";
    String MachineNamesFileTxt=  "src/main/java/com/example/l/txt/MachinesNamesFile.txt";
    String ProjectFileTxt=  "src/main/java/com/example/l/txt/Project.txt";
    String IntrestsFileTxt=  "src/main/java/com/example/l/txt/InterestFile.txt";
    String TeamsFileTxt=  "src/main/java/com/example/l/txt/TeamsFile.txt";
    String ReservationTxt=  "src/main/java/com/example/l/txt/RecervationFile.txt";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        if (Login.EnteredUser().getId().startsWith("s")){
            projectBox.getItems().addAll(loadProjects());
            TeamsBox.getItems().addAll(loadTeams());
        }else {
            projectBox.getItems().addAll(loadFromFile(ProjectFileTxt));
            TeamsBox.getItems().addAll(loadFromFile(TeamsFileTxt));
        }
        machineBox.getItems().addAll(loadFromFile(MachineNamesFileTxt));
        interestsBox.getItems().addAll(loadFromFile(IntrestsFileTxt));
    }
    private String[] loadFromFile(String fileName) {
        try {
            // Read lines from the file
            List<String> lines = Files.readAllLines(Paths.get(fileName));

            // Skip the first line and extract machine names from the remaining lines
            return lines.stream()
                    .skip(1) // Skip the first line
                    .map(line -> line.split(",")[0].trim())
                    .toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0]; // or you can return null or handle it differently based on your requirements
        }
    }
    private String[] loadProjects() {
        List<String> projectsList = new ArrayList<>();

        try (Scanner readMember = new Scanner(new File(MemberFileTxt));
             Scanner readProject = new Scanner(new File(ProjectFileTxt));) {

            readMember.nextLine(); // Skip the header line
            String memberName = null;

            // Find the member by ID
            while (readMember.hasNext()) {
                String[] memberLine = readMember.nextLine().split(", ");
                if (memberLine[1].equals(Login.EnteredUser().getId())) {
                    memberName = memberLine[0]; // Assign memberName value
                    break;
                }
            }
            readMember.close();

            // Collect projects associated with the member
            while (readProject.hasNext()) {
                String[] lineP = readProject.nextLine().split(", ");
                if (memberName != null && Member.getTeams(memberName).contains(lineP[1])) {
                    projectsList.add(lineP[0]);
                }
            }
            readProject.close();

            // Check if no projects were found
            if (projectsList.isEmpty()) {
                projectsList.add("No Projects");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }

        // Convert the list to an array
        return projectsList.toArray(new String[0]);
    }

    private String[] loadTeams() {
        ArrayList<String> TeamsList = new ArrayList<>();

        try (Scanner readMember = new Scanner(new File(MemberFileTxt))) {

            readMember.nextLine(); // Skip the header line
            String memberName = null;

            // Find the member by ID
            while (readMember.hasNext()) {
                String[] memberLine = readMember.nextLine().split(", ");
                if (memberLine[1].equals(Login.EnteredUser().getId())) {
                    memberName = memberLine[0]; // Assign memberName value
                    break;
                }
            }
            readMember.close();

            TeamsList = Member.getTeams(memberName);

            if (TeamsList.size()==0) {
                TeamsList.add("No Projects");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return TeamsList.toArray(new String[0]);
    }


    @FXML
    void onSave(ActionEvent event) {
        try {
            // Retrieve data from UI components
            String reservationId = reservationIdField.getText();
            String projectName = projectBox.getValue();
            String teamsId = TeamsBox.getValue();
            String selectedDate = String.valueOf(TimeDatePicker.getValue());
            String machineName = machineBox.getValue();
            String interest = interestsBox.getValue();

            // Validate the data
            if ("Select Project".equals(projectName) || selectedDate == null || machineName == null || interest== null || teamsId==null) {
                showAlert("Validation Error", "Please fill in all required fields (Project, Date, Machine, interest).");
                return; // Stop further processing
            }

            String Availability = isMachineFree(machineName, selectedDate);

            // Check if the selected machine is free on the selected date
            if (Availability!=reservationId && Availability!=null) {
                showAlert("Machine Not Available", "The selected machine is not available on the chosen date. Please choose another date or machine.");
                return;
            }

            if (projectName=="No Projects") {
                showAlert("Validation Error", "You can't reserve a machine when you don't have a project.");
                return; // Stop further processing
            }

            // Instantiate Reservation class
            Reservation reservation = new Reservation();

            // Call addReservation method
            boolean isEdited = reservation.editReservation(reservationId,projectName, selectedDate, machineName, teamsId, interest);

            if (!isEdited){
                showAlert("Error", "We couldn't find the reservation ID, try again");
            }else {
                showAlert("Edit Reservation","your changes are saved successfully!");
            }

        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }


    private void suggestMachines(String selectedInterest) throws FileNotFoundException {
        List<String> suggestedMachines = new ArrayList<>();
        Scanner scanner = new Scanner(new File(MachineNamesFileTxt));

        // Reading each line and converting it into an array
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] machineInfo = line.split(", ");
            if (machineInfo.length >= 3 && machineInfo[2].trim().equals(selectedInterest)) {
                suggestedMachines.add(machineInfo[0].trim());
            }
        }
        scanner.close();

        // Display the suggested machines in a message
        String message = suggestedMachines.isEmpty()
                ? "No suggested machines found for the selected interest."
                : "Suggested Machines: " + String.join(", ", suggestedMachines);

        if (selectedInterest.equals("Select Interests")){
            showAlert("Error", "Select an interest first..");
        }else {
            showAlert("Suggested Machines", message);
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private String  isMachineFree(String machineName, String selectedDate) throws IOException {
        Scanner scanner = new Scanner(new File(MachineFileTxt));
        String ReservationID = null;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] machineInfo = line.split(", ");
            String machine = machineInfo[0].trim();
            String reservedDate = machineInfo[2];
            String reservedId = machineInfo[3];

            // Check if the machine matches and the date is the same
            if (machine.equals(machineName) && selectedDate.equals(reservedDate)) {
                ReservationID=reservedId; // Machine is not free on the selected date
            }
        }

        scanner.close();

        return ReservationID;
    }

    @FXML
    void onSuggest(ActionEvent event) throws FileNotFoundException {
        suggestMachines(interestsBox.getValue());
    }

}

