package com.example.l;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

// Define file paths for various text files
public class HelloController implements Initializable {
    String MachineNamesFileTxt=  "src/main/java/com/example/l/txt/MachinesNamesFile.txt";
    String ProjectFileTxt=  "src/main/java/com/example/l/txt/Project.txt";
    String TeamFileTxt=  "src/main/java/com/example/l/txt/TeamsFile.txt";
    String MemberFileTxt=  "src/main/java/com/example/l/txt/Member.txt";

    // FXML elements
    @FXML
    private VBox pnitems = null;

    @FXML
    private Pane new_Reserve = null;

    private boolean isEditing = false;

    @FXML
    private Button addReservationButton;

    @FXML
    private Button editDeleteButton;

    @FXML
    private Button viesReservationButton;

    @FXML
    private Label Machines_num;

    @FXML
    private Label Projects_num;

    @FXML
    private Button Save_Button;

    @FXML
    private Label Teams_num;

    @FXML
    private Label Top1_Machine1;

    @FXML
    private Label Top1_Project;

    @FXML
    private Label Top1_member;

    @FXML
    private Label Top2_Machine1;

    @FXML
    private Label Top2_Project;

    @FXML
    private Label Top2_member;

    @FXML
    private Label Top3_Machine1;

    @FXML
    private Label Top3_Project;

    @FXML
    private Label Top3_member;

    @FXML
    private Label Top4_Machine1;

    @FXML
    private Label Top4_Project;

    @FXML
    private Label Top4_member;

    @FXML
    private Label Top5_Machine1;

    @FXML
    private Label Top5_Project;

    @FXML
    private Label Top5_member;

    @FXML
    private Button Top5_Team_Button;

    @FXML
    private Label Users_num;

    // Initialize method
    @Override
    public void initialize(URL location, ResourceBundle resource)  {
        // Load machine viewer and reservation pane
        machineLoad("Machine_Admin_viewer.fxml");

        try {
            Node node = FXMLLoader.load(getClass().getResource("New_Reservation.fxml"));
            new_Reserve.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set top teams, top members, and top machines
        setTopTeams();
        set_Top_members();
        set_Top_Machines();

        // Count the number of entries in various files and update corresponding labels
        try {
            BufferedReader reader = new BufferedReader(new FileReader(MachineNamesFileTxt));
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            reader.close();
            Machines_num.setText(String.valueOf(lines));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(TeamFileTxt));
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            reader.close();
            Teams_num.setText(String.valueOf(lines));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(MemberFileTxt));
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            reader.close();
            Users_num.setText(String.valueOf(lines));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(ProjectFileTxt));
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            reader.close();
            Projects_num.setText(String.valueOf(lines));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load reservation pane using the provided FXML file name
    private void loadReservationPane(String fxmlFileName) {
        new_Reserve.getChildren().clear();
        try {
            Node node = FXMLLoader.load(getClass().getResource(fxmlFileName));
            new_Reserve.getChildren().add(node);
        } catch (IOException e) {
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
        loadReservationPane("viewReservaationAdmin.fxml");
    }

    // Set the top teams based on machine usage
    private void setTopTeams() {
        String filePath = ProjectFileTxt;
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            Map<String, Integer> usageMap = new HashMap<>();

            for (String line : lines) {
                String[] parts = line.split(",");
                // Skip header or improperly formatted lines
                if (parts.length >= 4 && isInteger(parts[3].trim())) {
                    String projectName = parts[0].trim();
                    int usage = Integer.parseInt(parts[3].trim());
                    usageMap.put(projectName, usageMap.getOrDefault(projectName, 0) + usage);
                }
            }

            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(usageMap.entrySet());
            sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            // Set the labels with the top project names based on machine usage
            List<Label> labels = Arrays.asList(Top1_Project, Top2_Project, Top3_Project, Top4_Project, Top5_Project);
            for (int i = 0; i < labels.size(); i++) {
                if (i < sortedEntries.size()) {
                    labels.get(i).setText(sortedEntries.get(i).getKey());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Check if a string represents an integer
    private static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // Set the top machines based on machine usage
    private void set_Top_Machines() {
        String filePath = ProjectFileTxt;
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            Map<String, Integer> machineUsageMap = new HashMap<>();
            Map<String, Integer> teamUsageMap = new HashMap<>();

            for (String line : lines) {
                String[] parts = line.split(",");
                // Skip header or improperly formatted lines
                if (parts.length > 3 && isInteger(parts[3].trim())) {
                    // Assuming the fourth element indicates the usage count
                    int usageCount =

                            Integer.parseInt(parts[3].trim());

                    // Process the remaining elements as machine names
                    for (int i = 4; i < parts.length; i++) {
                        String machineName = parts[i].trim();
                        machineUsageMap.put(machineName, machineUsageMap.getOrDefault(machineName, 0) + usageCount);
                    }

                    String teamName = parts[0].trim();
                    teamUsageMap.put(teamName, teamUsageMap.getOrDefault(teamName, 0) + usageCount);
                }
            }

            // Sort entries by value (usage count)
            List<Map.Entry<String, Integer>> sortedMachineEntries = new ArrayList<>(machineUsageMap.entrySet());
            sortedMachineEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            List<Map.Entry<String, Integer>> sortedTeamEntries = new ArrayList<>(teamUsageMap.entrySet());
            sortedTeamEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            // Set the labels for top machines
            List<Label> machineLabels = Arrays.asList(Top1_Machine1, Top2_Machine1, Top3_Machine1, Top4_Machine1, Top5_Machine1);
            for (int i = 0; i < machineLabels.size(); i++) {
                if (i < sortedMachineEntries.size()) {
                    machineLabels.get(i).setText(sortedMachineEntries.get(i).getKey());
                }
            }

            // Set the labels for top teams
            List<Label> teamLabels = Arrays.asList(Top1_Project, Top2_Project, Top3_Project, Top4_Project, Top5_Project);
            for (int i = 0; i < teamLabels.size(); i++) {
                if (i < sortedTeamEntries.size()) {
                    teamLabels.get(i).setText(sortedTeamEntries.get(i).getKey());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Set the top members based on the number of machines they have
    private void set_Top_members() {
        String filePath = MemberFileTxt;
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath)).stream().skip(1).collect(Collectors.toList());
            Map<String, Integer> nameToMachineCountMap = new HashMap<>();

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length > 3) {
                    String name = parts[0].trim(); // Name is at index 0
                    int machineCount = Integer.parseInt(parts[3].trim()); // Machine count is at index 3
                    nameToMachineCountMap.merge(name, machineCount, Integer::sum);
                }
            }

            // Sort the entries by the number of machines in descending order, and then by name in alphabetical order
            List<String> sortedNames = nameToMachineCountMap.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                            .thenComparing(Map.Entry.comparingByKey()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            // Set the labels with the sorted member names
            if (sortedNames.size() > 0) {
                Top1_member.setText(sortedNames.get(0));
            }
            if (sortedNames.size() > 1) {
                Top2_member.setText(sortedNames.get(1));
            }
            if (sortedNames.size() > 2) {
                Top3_member.setText(sortedNames.get(2));
            }
            if (sortedNames.size() > 3) {
                Top4_member.setText(sortedNames.get(3));
            }
            if (sortedNames.size() > 4) {
                Top5_member.setText(sortedNames.get(4));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing the number of machines from the file.");
            e.printStackTrace();
        }
    }
}